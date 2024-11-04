package com.demo.springboot.service.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.springboot.dao.ClientDao;
import com.demo.springboot.dao.PermissionDao;
import com.demo.springboot.dao.RoleDao;
import com.demo.springboot.entity.Client;
import com.demo.springboot.entity.Permission;
import com.demo.springboot.entity.Role;
import com.demo.springboot.model.res.PermissionVo;

@Service
public class ErpUserDetailsService implements UserDetailsService {

    @Autowired
    ClientDao clientDao;

    @Autowired
    RoleDao roleDao;

    public ErpUserDetails loadUserByClientId(String clientId) throws UsernameNotFoundException {

        // Get API Client
        Client client = clientDao.findApiClientById(clientId);

        if (client == null) {
            throw new UsernameNotFoundException(clientId + " is not found");
        }

        List<PermissionVo> permissions = new ArrayList<>();
        for (Role r : client.getRoles()) {
            for (Permission p : r.getPermissions()) {
                PermissionVo vo = PermissionVo.builder().operator(p.getOperator()).name(p.getName()).build();
                if (!permissions.contains(vo)) {
                    permissions.add(vo);
                    System.out.println(vo);
                }
            }
        }

        return ErpUserDetails.builder()
                .clientId(client.getId())
                .username(client.getUsername())
                .password(client.getUserSecret())
                .authorities(client.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()))
                .permissions(permissions)
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }

}
