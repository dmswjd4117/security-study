package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Entity
@Setter @Getter
public class ResourceRole {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("method", resource.getHttpMethod())
                .append("role_name", role.getRoleName())
                .toString();
    }
}
