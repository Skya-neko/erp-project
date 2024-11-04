package com.demo.springboot.model.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GenTokenResVo {
    private String code;
    private String msg;
    private String token;
    private String loginTime;
    private UserInfoVO userInfo;
}
