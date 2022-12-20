package com.kristian.socmed.service;

import org.springframework.stereotype.Service;

import com.kristian.socmed.model.entity.Role;
import com.kristian.socmed.repository.RoleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleService {
    private RoleRepository roleRepository;
    public void addRole(Role role) {
        roleRepository.save(role);
    }
}
