package com.example.demo.service;

import com.example.demo.domain.ResourceRole;
import com.example.demo.repository.ResourceRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityResourceService {

    private final ResourceRoleRepository resourceRoleRepository;

    public SecurityResourceService(ResourceRoleRepository resourceRoleRepository) {
        this.resourceRoleRepository = resourceRoleRepository;
    }

    public List<ResourceRole> getResourceAndRoles() {
        return resourceRoleRepository.findAll();
    }
}
