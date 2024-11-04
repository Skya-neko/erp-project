package com.demo.springboot.entity;

import java.util.List;

import lombok.Data;

@Data
public class Client {

    private String id;

    String username;
    private String userSecret;
    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

}
