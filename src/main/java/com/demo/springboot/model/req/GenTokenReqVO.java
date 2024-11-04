package com.demo.springboot.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GenTokenReqVO {

    private String clientId;
    private String clientSecret;

}
