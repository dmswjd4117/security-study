package com.example.demo.domain;

import javax.persistence.*;

@Entity
public class ResourceRole {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
