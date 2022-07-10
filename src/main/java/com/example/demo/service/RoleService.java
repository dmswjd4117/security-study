package com.example.demo.service;

import com.example.demo.domain.Role;
import com.example.demo.repository.ResourceRepository;
import com.example.demo.repository.ResourceRoleRepository;
import com.example.demo.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void createRole(Role role) {
        roleRepository.save(role);
    }

    public Optional<Role> getRole(long id) {
        return roleRepository.findById(id);
    }

    public String getAllRoleNames() {
        return getAllRoles().stream().map(Role::getRoleName).collect(Collectors.joining(","));
    }
}
