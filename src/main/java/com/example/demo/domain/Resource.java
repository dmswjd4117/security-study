package com.example.demo.domain;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Setter @Getter
public class Resource implements Serializable {

    @Id
    @Column(name = "resource_id", nullable = false)
    @GeneratedValue
    private Long id;

    @NotNull
    private String resourceName;

    @NotNull
    private String httpMethod;

    @NotNull
    private int orderNum;

    @NotNull
    private String resourceType;

    @OneToMany(mappedBy = "resource", cascade = CascadeType.REMOVE)
    private List<ResourceRole> resourceRoles = new ArrayList<>();

    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("httpMethod", httpMethod)
                .append("orderNum", orderNum)
                .append("resourceType", resourceType)
                .append("resourceRoles", resourceRoles.stream()
                        .map(ResourceRole::toString)
                        .collect(Collectors.joining(",")))
                .toString();
    }

}
