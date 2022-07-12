package com.example.demo.controller.admin;

import com.example.demo.controller.dto.ResourceDto;
import com.example.demo.domain.Resource;
import com.example.demo.security.metadataSource.UrlSecurityMetadataSource;
import com.example.demo.service.ResourcesService;
import com.example.demo.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/resource")
public class ResourcesController {

    private final ResourcesService resourcesService;
    private final RoleService roleService;
    private final UrlSecurityMetadataSource urlSecurityMetadataSource;

    public ResourcesController(ResourcesService resourcesService, RoleService roleService, UrlSecurityMetadataSource urlSecurityMetadataSource) {
        this.resourcesService = resourcesService;
        this.roleService = roleService;
        this.urlSecurityMetadataSource = urlSecurityMetadataSource;
    }

    //    리소스 삭제
    @GetMapping(value="/delete/{id}")
    public String removeResource(@PathVariable String id, Model model){
        resourcesService.deleteResource(Long.valueOf(id));
        urlSecurityMetadataSource.reload();
        return "redirect:/admin/resource/list";
    }

    //    리소스 생성
    @PostMapping()
    public String createResource(ResourceDto resourceDto){
        resourcesService.createResource(ResourceDto.toEntity(resourceDto), resourceDto.getRoles().split(","));
        urlSecurityMetadataSource.reload();
        return "redirect:/admin/resource/list";
    }

    //    리소스 생성 view
    @GetMapping()
    public String showResourceForm(Model model){

        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setRoles(roleService.getAllRoleNames());

        model.addAttribute("resourceDto", resourceDto);

        return "admin/resource/createResource";
    }

    //    리소스 목록 view
    @GetMapping("/list")
    public String showResources(Model model){

        List<Resource> resources = resourcesService.getResources();
        model.addAttribute("resources", resources);

        return "admin/resource/list";
    }

    //    리소스 조회 view
    @GetMapping(value="/{id}")
    public String showResourceDetail(@PathVariable long id, Model model){

        Resource resource = resourcesService.getResource(id);
        model.addAttribute("resourceDto", ResourceDto.from(resource));

        return "admin/resource/detail";
    }
}
