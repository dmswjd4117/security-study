package com.example.demo.security.metadataSource;

import com.example.demo.service.SecurityResourceService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UrlResourcesMapFactoryBean implements FactoryBean<LinkedHashMap<RequestMatcher, List<ConfigAttribute>>> {

    private final SecurityResourceService securityResourceService;
    private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resources;

    public UrlResourcesMapFactoryBean(SecurityResourceService securityResourceService) {
        this.securityResourceService = securityResourceService;
    }

    @Override
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getObject(){
        if(resources == null){
            init();
        }

        return resources;
    }

    private void init(){
        resources = securityResourceService.getResourceAndRoles();
    }

    @Override
    public Class<?> getObjectType() {
        return Map.class;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}

