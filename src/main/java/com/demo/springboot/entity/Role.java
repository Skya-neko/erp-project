package com.demo.springboot.entity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
public class Role {

    private Long id;

    @NonNull
    private String name;

    private List<Permission> permissions;

    private LocalDateTime createDate = LocalDateTime.now();

    LocalDateTime updateDate = LocalDateTime.now();

}