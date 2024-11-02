package com.demo.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springboot.dao.model.Client;
import com.demo.springboot.service.ClientService;

import ch.qos.logback.core.joran.conditional.ThenAction;

@RestController
public class ClientController {

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
