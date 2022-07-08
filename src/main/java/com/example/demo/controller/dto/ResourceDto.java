package com.example.demo.controller.dto;

import com.example.demo.domain.Resource;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString @Setter
public class ResourceDto {

    private Long id;
    private String resourceName;
    private String httpMethod;
    private int orderNum;
    private String resourceType;
    private String roles;

    public static Resource toEntity(ResourceDto resourceDto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(resourceDto, Resource.class);
    }

    public static ResourceDto from(Resource resource){
        ModelMapper modelMapper = new ModelMapper();

        String roles = resource.getResourceRoles().stream().
                map(resourceRole -> resourceRole.getRole().getRoleName())
                .collect(Collectors.joining(","));

        ResourceDto resourceDto = modelMapper.map(resource, ResourceDto.class);
        resourceDto.setRoles(roles);

        return resourceDto;
    }
}
