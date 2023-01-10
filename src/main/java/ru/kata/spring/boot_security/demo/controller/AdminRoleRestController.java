package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class AdminRoleRestController {

    private final RoleService roleService;

    @Autowired
    public AdminRoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/getRoles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getRole(), HttpStatus.OK);
    }
}
