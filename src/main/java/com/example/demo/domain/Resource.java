package com.example.demo.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Resource implements Serializable {

    @Id
    @Column(name = "resource_id", nullable = false)
    private Long id;

    private String resourceName;

    private String httpMethod;

    private int orderNum;

    private String resourceType;

    @OneToMany(mappedBy = "resource")
    private List<ResourceRole> resourceRole = new ArrayList<>();

    public Resource() {}
}
