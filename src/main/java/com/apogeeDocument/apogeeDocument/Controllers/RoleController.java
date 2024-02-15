package com.apogeeDocument.apogeeDocument.Controllers;

import com.apogeeDocument.apogeeDocument.entites.Role;
import com.apogeeDocument.apogeeDocument.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "role")
public class RoleController {

    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody Role role){
        this.roleService.create(role);
    }

}
