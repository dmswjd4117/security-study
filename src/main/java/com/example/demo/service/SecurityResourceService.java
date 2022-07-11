package com.example.demo.service;

import com.example.demo.domain.ResourceRole;
import com.example.demo.repository.ResourceRoleRepository;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class SecurityResourceService {

    private final ResourceRoleRepository resourceRoleRepository;

    public SecurityResourceService(ResourceRoleRepository resourceRoleRepository) {
        this.resourceRoleRepository = resourceRoleRepository;
    }

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceAndRoles() {
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resources = new LinkedHashMap<>();
        List<ResourceRole> resourceRoles = resourceRoleRepository.findAll();

        resourceRoles.forEach(resourceRole -> {
            RequestMatcher resource = new AntPathRequestMatcher(resourceRole.getResource().getHttpMethod());
            ConfigAttribute configAttribute = new SecurityConfig(resourceRole.getRole().getRoleName());

            if(resources.containsKey(resource)){
                resources.get(resource).add(configAttribute);
            }else{
                List<ConfigAttribute> list = new ArrayList<>();
                list.add(configAttribute);
                resources.put(resource,list);
            }
        });

        return resources;
    }
}
