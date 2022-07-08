package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
@ToString
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Long id;

    private String roleName;

    private String roleDescription;

    @OneToMany(mappedBy = "role")
    @ToString.Exclude
    private List<MemberRole> memberRoles = new ArrayList<>();

    @OneToMany(mappedBy = "role")
    @ToString.Exclude
    private List<ResourceRole> resourceRoles = new ArrayList<>();
}
