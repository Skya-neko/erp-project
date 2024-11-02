package com.demo.springboot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.demo.springboot.dao.model.Client;

@Mapper
public interface ClientDao {
    public List<Client> selectClients();
}
