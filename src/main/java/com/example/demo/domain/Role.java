package com.example.demo.domain;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Long id;

    private String roleName;

    private String roleDescription;

    @OneToMany(mappedBy = "role")
    private List<MemberRole> memberRoles = new ArrayList<>();

    @OneToMany(mappedBy = "role")
    private List<ResourceRole> resourceRoles = new ArrayList<>();

    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("role_name", roleName)
                .append("roleDescription", roleDescription)
                .append("memberRoles", memberRoles)
                .append("resourceRoles", resourceRoles)
                .build();
    }
}
