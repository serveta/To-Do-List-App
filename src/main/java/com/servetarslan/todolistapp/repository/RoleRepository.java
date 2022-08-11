package com.servetarslan.todolistapp.repository;

import com.servetarslan.todolistapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
