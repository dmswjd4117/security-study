package com.example.demo.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Long id;

    private String roleName;

    @OneToMany(mappedBy = "role")
    private List<MemberRole> memberRoles = new ArrayList<>();

    @OneToMany(mappedBy = "role")
    private List<ResourceRole> resourceRoles = new ArrayList<>();
}
