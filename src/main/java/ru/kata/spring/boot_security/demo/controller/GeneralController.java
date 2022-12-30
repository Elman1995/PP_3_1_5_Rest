package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.security.Principal;
import java.util.Collections;

@Controller
public class GeneralController {
    private final UserService userService;

    @Autowired
    public GeneralController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getAllUser(Model model, Principal principal) {
        User activeUser = userService.findByUsername(principal.getName());
        model.addAttribute("nav-user-table", userService.getUser());
        model.addAttribute("userRoles", activeUser.getRoles());
        model.addAttribute("userActive", userService.findByUsername(principal.getName()));
        return "admin";
    }

    @GetMapping("/user")
    public String index(@AuthenticationPrincipal User userActive, Model model) {
        model.addAttribute("userActive", userActive);
        return "user";
    }

}
