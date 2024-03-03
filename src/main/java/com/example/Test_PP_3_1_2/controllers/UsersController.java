package com.example.Test_PP_3_1_2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UsersController {
    @GetMapping("/admin")
    public String getAdminPage() {

        return "admin";

    }

    @GetMapping("/user")
    public String getUserPage() {

        return "user";

    }
}
