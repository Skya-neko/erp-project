package com.demo.springboot.filter;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.demo.springboot.constant.HttpRespCode;
import com.demo.springboot.model.BaseRs;

import org.springframework.security.core.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GalaxyAuthEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LogManager.getLogger(GalaxyAuthEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {
        logger.info("============ Start CafeAuthenticationEntryPoint.commence() ============");
        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");

        BaseRs resVo = new BaseRs();

        if (e instanceof BadCredentialsException) {
            String[] errorArr = e.getMessage().split("-");
            if (errorArr.length == 2) {
                resVo.setCode(errorArr[0]);
                resVo.setMsg(errorArr[1]);
            } else {
                resVo.setCode(HttpRespCode.Common.FAILURE.getCode());
                resVo.setMsg(e.getMessage());
            }
        } else {
            resVo.setCode(HttpRespCode.Common.FAILURE.getCode());
            resVo.setMsg(e.getMessage());
        }

        // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        response.getOutputStream().print(new Gson().toJson(resVo));
    }

}
