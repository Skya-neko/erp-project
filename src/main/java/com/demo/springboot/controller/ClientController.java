package com.demo.springboot.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springboot.constant.ClientConstants;
import com.demo.springboot.constant.HttpRespCode;
import com.demo.springboot.entity.Client;
import com.demo.springboot.model.ApiToken;
import com.demo.springboot.model.req.GenTokenReqVO;
import com.demo.springboot.model.res.GenTokenResVo;
import com.demo.springboot.model.res.UserInfoVO;
import com.demo.springboot.service.ClientService;

import ch.qos.logback.core.joran.conditional.ThenAction;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.demo.springboot.service.TokenService;
import com.demo.springboot.service.security.ErpUserDetails;
import com.demo.springboot.service.security.ErpUserDetailsService;

@RestController
@RequestMapping("/api/auth")
public class ClientController {
    private static final Logger logger = LogManager.getLogger(ClientController.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ErpUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("signIn")
    public ResponseEntity<GenTokenResVo> onSignIn(@RequestBody GenTokenReqVO req,
            HttpServletRequest request) {
        logger.info("============ Start SystemController.genApiToken()    ============");
        GenTokenResVo res = null;
        try {
            // 1. search user id is exist or not
            ErpUserDetails userDetails = userDetailsService.loadUserByClientId(req.getClientId());
            System.out.println(userDetails);

            // 2. check password
            if (!passwordEncoder.matches(req.getClientSecret(),
                    userDetails.getPassword())) {
                res = new GenTokenResVo();
                res.setCode(HttpRespCode.Common.FAILURE.getCode());
                res.setMsg(HttpRespCode.Common.FAILURE.getMsg());
                res.setToken(null);
                return ResponseEntity.ok(res);
            }

            // 2. gen jwt token
            ApiToken token = tokenService.generateToken(userDetails.getClientId(),
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(ClientConstants.EXPIRATION_TIME));

            // 3. create user info
            UserInfoVO uInfo = UserInfoVO.builder()
                    .userId(userDetails.getClientId())
                    .userName(userDetails.getUsername())
                    .menu(userDetails.getPermissions())
                    .build();

            //
            res = new GenTokenResVo();
            res.setCode(HttpRespCode.Common.SUCCESS.getCode());
            res.setMsg(HttpRespCode.Common.SUCCESS.getMsg());
            res.setToken(token.getAccess());
            res.setUserInfo(uInfo);

        } catch (Exception e) {
            res = new GenTokenResVo();
            res.setCode(HttpRespCode.Common.FAILURE.getCode());
            res.setMsg(HttpRespCode.Common.FAILURE.getMsg());
            res.setToken(null);

        } finally {
            logger.info("============ End SystemController.genApiToken() ============");
        }

        return ResponseEntity.ok(res);
    }

    @Autowired
    private ClientService clientService;

    @GetMapping(value = "/clients")
    public List<Client> getClients() throws Exception {
        List<Client> clients = null;
        try {
            System.out.println("hello");
            clientService.printAll();
            System.out.println("hello1");
            clients = clientService.getAllClients();
        } catch (Exception e) {
            e.printStackTrace();

        }

        return clients;
    }

}
