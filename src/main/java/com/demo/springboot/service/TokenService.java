package com.demo.springboot.service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.demo.springboot.model.ApiToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.security.auth.message.AuthException;

@Component
public class TokenService implements Serializable {
    private static final Logger logger = LogManager.getLogger(TokenService.class);

    private static final String CLAIM_CLIENT_ID = "clientId";

    private static final String SECRET = "2bb80d537b1da3e38bd30361aa855686bde0eacd7162fef6a25fe97bf527a25b";

    // getnerate jwt token
    public ApiToken generateToken(String clientId, LocalDateTime issueLocalDateTime, LocalDateTime validLocalDateTime) {
        logger.info("============ Start TokenService.generateToken() ============");

        try {
            Map<String, Object> claims = new HashMap<>(16);
            claims.put(CLAIM_CLIENT_ID, clientId);

            String tokenkey = Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(Date.from(validLocalDateTime.atZone(ZoneId.systemDefault()).toInstant()))
                    .signWith(SignatureAlgorithm.HS512, SECRET)
                    .compact();

            logger.info(tokenkey);

            return ApiToken.builder()
                    .access(tokenkey)
                    .clientId(clientId)
                    .issueTime(issueLocalDateTime)
                    .validTime(validLocalDateTime)
                    .build();
        } catch (Exception e) {
            // todo: handle exception
        } finally {
            logger.info("============ End TokenService.generateToken() ============");
        }

        return ApiToken.builder().build();
    }

    public Boolean isTimeout(LocalDateTime vDateTime) {
        if (Objects.isNull(vDateTime)) {
            return false;
        }

        return vDateTime.isAfter(LocalDateTime.now());
    }

    public String getClientId(String token) {
        return (String) getClaimsFromToken(token).get(CLAIM_CLIENT_ID);
    }

    /**
     * 驗證JWT
     */
    public void validateToken(String token) throws AuthException {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token);
        } catch (SignatureException e) {
            throw new AuthException("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            throw new AuthException("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            throw new AuthException("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            throw new AuthException("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            throw new AuthException("JWT token compact of handler are invalid");
        }
    }

    private Claims getClaimsFromToken(String token) {
        try {
            // Get Claims from valid token
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // Get Claims from expired token
            return e.getClaims();
        }
    }

}
