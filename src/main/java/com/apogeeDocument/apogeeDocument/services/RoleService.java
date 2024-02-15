package com.apogeeDocument.apogeeDocument.services;

import com.apogeeDocument.apogeeDocument.entites.Role;
import com.apogeeDocument.apogeeDocument.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void create(Role role){
        this.roleRepository.save(role);
    }
}
