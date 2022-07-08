package com.example.demo.controller.admin;

import com.example.demo.controller.dto.ResourceDto;
import com.example.demo.domain.Resource;
import com.example.demo.service.ResourcesService;
import com.example.demo.service.RoleService.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ResourcesController {

    private final ResourcesService resourcesService;
    private final RoleService roleService;

    public ResourcesController(ResourcesService resourcesService, RoleService roleService) {
        this.resourcesService = resourcesService;
        this.roleService = roleService;
    }

//    리소스 목록들
    @GetMapping(value="/admin/resources")
    public String showResources(Model model){

        List<Resource> resources = resourcesService.getResources();
        model.addAttribute("resources", resources);

        return "admin/resource/list";
    }


//    리소스
    @GetMapping(value="/admin/resources/{id}")
    public String showResourceDetail(@PathVariable long id, Model model){

        Resource resource = resourcesService.getResource(id);
        model.addAttribute("resourceDto", ResourceDto.from(resource));

        return "admin/resource/detail";
    }

    @GetMapping(value="/admin/resources/delete/{id}")
    public String removeResource(@PathVariable String id, Model model){

        Resource resources = resourcesService.getResource(Long.valueOf(id));
        resourcesService.deleteResource(Long.valueOf(id));

        return "redirect:/admin/resources";
    }

//    리소스 생성
    @PostMapping(value="/admin/resource")
    public String createResource(ResourceDto resourceDto){
        resourcesService.createResource(ResourceDto.toEntity(resourceDto), resourceDto.getRoles().split(","));
        return "redirect:/admin/resources";
    }

    @GetMapping(value="/admin/resource")
    public String showResourceForm(Model model){

        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setRoles(roleService.getAllRoleNames());

        model.addAttribute("resourceDto", resourceDto);

        return "admin/resource/createResource";
    }
}
