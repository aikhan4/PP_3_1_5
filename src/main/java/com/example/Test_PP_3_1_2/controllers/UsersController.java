package com.example.Test_PP_3_1_2.controllers;

import com.example.Test_PP_3_1_2.models.Role;
import com.example.Test_PP_3_1_2.models.User;
import com.example.Test_PP_3_1_2.security.UserWrapper;
import com.example.Test_PP_3_1_2.service.RoleService;
import com.example.Test_PP_3_1_2.service.UserService;
import com.example.Test_PP_3_1_2.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsersController {
    @Autowired
    private ApplicationContext context;

    @GetMapping(value = "/admin")
    public String users(Model model) {
        model.addAttribute("usersList", context.getBean("userService", UserService.class).findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserWrapper) authentication.getPrincipal()).getUser();
        model.addAttribute("cookieUser", user);
        return "admin";
    }
    @GetMapping(value = "/add")
    public String addUserPage(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        model.addAttribute("error", bindingResult);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User cookieUser = ((UserWrapper) authentication.getPrincipal()).getUser();
        model.addAttribute("cookieUser", cookieUser);
        return "addUser";
    }
    @Transactional
    @PostMapping(value = "/add")
    public String addUser(@ModelAttribute("user") User user, BindingResult bindingResult, Model model, @RequestParam String role) {

        context.getBean("userValidator", UserValidator.class).validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult); // доработать на html странице addUser , добавить поле которое сообщает о том что пользователь создан, если была попытка создать пользователя
            return "addUser";
        }

        if (role.equals("ADMIN")) {
            Role adminRole = context.getBean("roleService", RoleService.class).findById((long) 1).get();
            Role userRole = context.getBean("roleService", RoleService.class).findById((long) 2).get();
            user.addRole(adminRole);
            user.addRole(userRole);
            adminRole.addUser(user);
            userRole.addUser(user);
        } else if (role.equals("USER")) {
            Role userRole = context.getBean("roleService", RoleService.class).findById((long) 2).get();
            user.addRole(userRole);
            userRole.addUser(user);
        } else if (role.isEmpty()) {
            Role userRole = context.getBean("roleService", RoleService.class).findById((long) 2).get();
            user.addRole(userRole);
            userRole.addUser(user);
        }

        context.getBean("userService", UserService.class).save(user);

        return "redirect:/admin";
    }
    @PatchMapping(value = "/change")
    public String changeUser(@ModelAttribute("user") User userUpdated, @RequestParam String role) {
        if (role.equals("ADMIN")) {
            Role adminRole = context.getBean("roleService", RoleService.class).findById((long) 1).get();
            Role userRole = context.getBean("roleService", RoleService.class).findById((long) 2).get();
            userUpdated.addRole(adminRole);
            userUpdated.addRole(userRole);
            adminRole.addUser(userUpdated);
            userRole.addUser(userUpdated);
        } else if (role.equals("USER")) {
            Role userRole = context.getBean("roleService", RoleService.class).findById((long) 2).get();
            userUpdated.addRole(userRole);
            userRole.addUser(userUpdated);
        } else if (role.isEmpty()) {
            Role userRole = context.getBean("roleService", RoleService.class).findById((long) 2).get();
            userUpdated.addRole(userRole);
            userRole.addUser(userUpdated);
        }
        context.getBean("userService", UserService.class).save(userUpdated);
        return "redirect:/admin";
    }
    @DeleteMapping(value = "/delete")
    public String deleteUser(@RequestParam Long id) {
        context.getBean("userService", UserService.class).deleteById(id);
        return "redirect:/admin";
    }
    @GetMapping(value = "/user")
    public String userPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserWrapper) authentication.getPrincipal()).getUser();
        model.addAttribute("user", user);

        return "userInfo";
    }
    @GetMapping(value = "/AdminInfo")
    public String adminInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserWrapper) authentication.getPrincipal()).getUser();
        model.addAttribute("user", user);

        return "adminInfo";
    }
}
