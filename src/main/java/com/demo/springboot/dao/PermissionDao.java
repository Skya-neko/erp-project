package com.demo.springboot.dao;

import com.demo.springboot.entity.Permission;

public interface PermissionDao {

    Permission findByName(String name);

    Permission findByNameAndOperator(String name, String operator);
}
