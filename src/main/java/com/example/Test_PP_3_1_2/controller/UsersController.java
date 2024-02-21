package com.example.Test_PP_3_1_2.controller;

import com.example.Test_PP_3_1_2.models.User;
import com.example.Test_PP_3_1_2.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsersController {
    @Autowired
    private ApplicationContext context;

    @GetMapping(value = "/")
    public String users(Model model) {
        model.addAttribute("usersList", context.getBean("userServiceImpl", UserServiceImpl.class).getAllUsers());
        return "users";
    }
    @GetMapping(value = "/add")
    public String addUserPage(@ModelAttribute("user") User user) {
        return "addUser";
    }
    @PostMapping(value = "/add")
    public String addUser(@ModelAttribute("user") User user) {
        context.getBean("userServiceImpl", UserServiceImpl.class).add(user);
        return "redirect:/";
    }
    @GetMapping(value = "/change")
    public String changeUserPage(Model model, @RequestParam(value = "id") Long id) {
        model.addAttribute("user", context.getBean("userServiceImpl", UserServiceImpl.class).getUser(id));
        return "changeUser";
    }
    @PatchMapping(value = "/change")
    public String changeUser(@ModelAttribute("user") User user) {
        context.getBean("userServiceImpl", UserServiceImpl.class).changeUser(user.getId(), user.getName(), user.getSurname());
        return "redirect:/";
    }
    @DeleteMapping(value = "/delete")
    public String deleteUser(@RequestParam Long id) {
        context.getBean("userServiceImpl", UserServiceImpl.class).deleteUser(id);
        return "redirect:/";
    }
}
