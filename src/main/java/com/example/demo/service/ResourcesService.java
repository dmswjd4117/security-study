package com.example.demo.service;

import com.example.demo.domain.Resource;
import com.example.demo.domain.ResourceRole;
import com.example.demo.repository.ResourceRepository;
import com.example.demo.repository.ResourceRoleRepository;
import com.example.demo.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ResourcesService {

    private final ResourceRepository resourceRepository;
    private final ResourceRoleRepository resourceRoleRepository;
    private final RoleRepository roleRepository;

    public ResourcesService(ResourceRepository resourceRepository, ResourceRoleRepository resourceRoleRepository, RoleRepository roleRepository) {
        this.resourceRepository = resourceRepository;
        this.resourceRoleRepository = resourceRoleRepository;
        this.roleRepository = roleRepository;
    }

    public List<Resource> getResources() {
        return resourceRepository.findAll();
    }

    public Resource getResource(Long id) {
        return resourceRepository.findById(id).orElseThrow(()->new RuntimeException(id+" not found"));
    }

    public void deleteResource(Long id) {
        resourceRepository.deleteById(id);
    }

    public void createResource(Resource resource, String[] roles) {
        Arrays.stream(roles).forEach(role ->{
            roleRepository.findByRoleName(role).ifPresent(findRole -> {
                ResourceRole resourceRole = new ResourceRole();
                resourceRole.setResource(resource);
                resourceRole.setRole(findRole);

                resourceRepository.save(resource);
                resourceRoleRepository.save(resourceRole);
            });
        });
    }
}
