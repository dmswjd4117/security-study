package com.example.demo.repository;

import com.example.demo.domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Resource findByResourceNameAndHttpMethod(String resourceName, String httpMethod);
}
