package com.example.demo.repository;

import com.example.demo.domain.ResourceRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResourceRoleRepository  extends JpaRepository<ResourceRole, Long> {

    Optional<ResourceRoleRepository> findByResourceId(long id);
}
