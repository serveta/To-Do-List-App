package com.servetarslan.todolistapp.service.impl;

import com.servetarslan.todolistapp.model.Role;
import com.servetarslan.todolistapp.repository.RoleRepository;
import com.servetarslan.todolistapp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role findOrCreate(String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
        return role;
    }

    @Override
    public Role getUserRole() {
        String roleName = "ROLE_USER";
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
        return role;
    }

    @Override
    public Role getAdminRole() {
        String roleName = "ROLE_ADMIN";
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
        return role;
    }
}
