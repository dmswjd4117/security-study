package com.example.demo.security.auth.metadataSource;

import com.example.demo.service.SecurityResourceService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class UrlSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

//    private final LinkedHashMap<RequestMatcher, List<ConfigAttribute>> requestMap = new LinkedHashMap<>();
    private final SecurityResourceService resourceService;

    public UrlSecurityMetadataSource(SecurityResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        HttpServletRequest request = ((FilterInvocation) object).getRequest();

        // todo: 데이터베이스랑 연동하기
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> requestMap = new LinkedHashMap<>();
        List<ConfigAttribute> l = List.of(new SecurityConfig("ROLE_ADMIN"));
        requestMap.put(new AntPathRequestMatcher("/admin/**"), l);

        for(Map.Entry<RequestMatcher, List<ConfigAttribute>> entry: requestMap.entrySet()){
            if(entry.getKey().matches(request)){
                return entry.getValue();
            }
        }

        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<>();
//        this.requestMap.values().forEach(allAttributes::addAll);
        return allAttributes;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
