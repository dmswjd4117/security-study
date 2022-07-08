package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter @ToString
public class Member {

    @Column(name = "member_id")
    @Id @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String password;

    @OneToMany(mappedBy = "member")
    private List<MemberRole> memberRoles = new ArrayList<>();
}
