package com.example.demo.controller.admin;

import com.example.demo.domain.Role;
import com.example.demo.controller.dto.RoleDto;
import com.example.demo.service.RoleService.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(value="/admin/roles")
    public String getRoles(Model model){
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "admin/role/list";
    }

    @GetMapping(value="/admin/roles/register")
    public String viewRoles(Model model){
        Role role = new Role();
        model.addAttribute("role", role);
        return "admin/role/detail";
    }

    @PostMapping(value="/admin/roles")
    public String createRole(RoleDto roleDto){
        ModelMapper modelMapper = new ModelMapper();
        Role role = modelMapper.map(roleDto, Role.class);
        roleService.createRole(role);
        return "redirect:/admin/roles";
    }

    @GetMapping(value="/admin/roles/{id}")
    public String getRole(@PathVariable String id, Model model){
        roleService.getRole(Long.parseLong(id)).ifPresent((role)->{
            model.addAttribute("role", role);
        });
        return "admin/role/detail";
    }
}
