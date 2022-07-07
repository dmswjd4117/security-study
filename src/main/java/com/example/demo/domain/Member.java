package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
@Setter @Getter @ToString
public class Member {

    @Column(name = "member_id")
    @Id @GeneratedValue
    private Long id;

    private String userName;

    private String email;

    private String password;

    @OneToMany(mappedBy = "member")
    private List<MemberRole> memberRoles = new ArrayList<>();
}
