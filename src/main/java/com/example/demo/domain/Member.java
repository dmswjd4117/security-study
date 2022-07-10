package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Setter @Getter
public class Member {

    @Column(name = "member_id")
    @Id @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String password;

    @OneToMany(mappedBy = "member")
    private List<MemberRole> memberRoles = new ArrayList<>();

    public Collection<? extends GrantedAuthority> getGrantedAuthorities() {
        return getMemberRoles()
                .stream()
                .map(MemberRole::getRole)
                .map(Role::getRoleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("name", name)
                .append("memberRoles", memberRoles.stream()
                        .map(MemberRole::toString)
                        .collect(Collectors.joining(",")))
                .toString();
    }
}
