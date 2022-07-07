package com.example.demo.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MemberRole {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
