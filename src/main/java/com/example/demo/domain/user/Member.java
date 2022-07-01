package com.example.demo.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter @Getter @ToString
public class Member {

    @Column(name = "user_id")
    @Id @GeneratedValue
    private Long id;

    private String userName;

    private String email;

    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private Set<Role> userRoles = new HashSet<>();
}
