package com.servetarslan.todolistapp.service;

import com.servetarslan.todolistapp.model.Role;

public interface RoleService {
    Role findOrCreate(String roleName);
    Role getBasicRole();
    Role getVipRole();
}
