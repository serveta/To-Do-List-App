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
    public Role getBasicRole() {
        String roleName = "ROLE_BASIC";
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
        return role;
    }

    @Override
    public Role getVipRole() {
        String roleName = "ROLE_VIP";
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
        return role;
    }
}
