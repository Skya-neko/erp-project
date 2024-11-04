package com.demo.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;

import com.demo.springboot.dao.ClientDao;
import com.demo.springboot.entity.Client;

@Service
public class ClientService {
    @Autowired
    private ClientDao clientDao;

    // get all clients
    @Transactional
    public List<Client> getAllClients() {
        return clientDao.selectClients();
    }

    @Async("taskExecutor")
    public void printAll() throws Exception {
        System.out.println(Thread.currentThread().getName());
        System.out.println("start printAll");
        Thread.sleep(1000);
        System.out.println("end printAll");
    }

}
