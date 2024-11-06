package com.demo.springboot.filter;

import org.apache.logging.log4j.Logger;

import java.io.IOException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.demo.springboot.constant.ClientConstants;
import com.demo.springboot.service.TokenService;
import com.demo.springboot.service.security.ErpUserDetails;
import com.demo.springboot.service.security.ErpUserDetailsService;

import io.micrometer.common.util.StringUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ErpAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LogManager.getLogger(ErpAuthenticationFilter.class);

    @Autowired
    ErpUserDetailsService userDetailsService;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        logger.info("============ Start GalaxyAuthenticationFilter.doFilterInternal() ============");
        try {
            // if it already had authorization
            logger.info("Authing... = " + SecurityContextHolder.getContext().getAuthentication());
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                filterChain.doFilter(request, response);
                return;
            }

            String requestUri = request.getRequestURI();
            String servletPath = request.getServletPath();
            logger.info("requestUri = {},  servletPath = {}", requestUri, servletPath);

            //
            String token = request.getHeader(ClientConstants.API_HEADER_STRING);
            if (!StringUtils.isBlank(token)) {
                authApiToken(token, request);
                filterChain.doFilter(request, response);
            } else {
                logger.info("No Token");
                filterChain.doFilter(request, response);
                return;
            }
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            new GalaxyAuthEntryPoint().commence(request, response, new BadCredentialsException(e.getMessage()));
        } finally {
            logger.info("============ End GalaxyAuthenticationFilter.doFilterInternal() ============");
        }
    }

    private void authApiToken(String token, HttpServletRequest request) throws Exception {
        try {
            // 1. check token
            if (StringUtils.isBlank(token)) {
                throw new Exception("Token is null");
            }
            logger.info("Pass: check token");

            // 2. check token is valid or not
            tokenService.validateToken(token);
            logger.info("Pass: Check token is valid or not ");

            // 3. check client id
            String clientId = tokenService.getClientId(token);
            ErpUserDetails userDetails = userDetailsService.loadUserByClientId(clientId);
            if (userDetails == null) {
                throw new Exception("User not found");
            }
            logger.info("Pass: check client id ");
            logger.info(userDetails);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            throw e;
        }

    }
}
