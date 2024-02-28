package com.example.Test_PP_3_1_2.controllers;

import com.example.Test_PP_3_1_2.models.User;
import com.example.Test_PP_3_1_2.security.UserWrapper;
import com.example.Test_PP_3_1_2.service.UserService;
import com.example.Test_PP_3_1_2.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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
    public String addUserPage(@ModelAttribute User user, BindingResult bindingResult, Model model) {
        model.addAttribute("bindingResult", bindingResult);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User cookieUser = ((UserWrapper) authentication.getPrincipal()).getUser();
        model.addAttribute("cookieUser", cookieUser);
        return "addUser";
    }
    @PostMapping(value = "/add")
    public String addUser(@ModelAttribute("user") User user, @RequestParam String role, BindingResult bindingResult, Model model) {

        context.getBean("userValidator", UserValidator.class).addValidate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult); // доработать на html странице addUser , добавить поле которое сообщает о том что пользователь создан, если была попытка создать пользователя
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User cookieUser = ((UserWrapper) authentication.getPrincipal()).getUser();
            model.addAttribute("cookieUser", cookieUser);
            return "addUser";
        }

        context.getBean("userService", UserService.class).save(user, role);

        return "redirect:/admin";
    }
    @PatchMapping(value = "/change")
    public String changeUser(@ModelAttribute("user") User userUpdated, @RequestParam String role, Model model, BindingResult bindingResult) {

        context.getBean("userValidator", UserValidator.class).changeValidate(userUpdated, bindingResult, role);

        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User cookieUser = ((UserWrapper) authentication.getPrincipal()).getUser();
            model.addAttribute("cookieUser", cookieUser);
            model.addAttribute("user", userUpdated);
            return "changeUser";
        }

        context.getBean("userService", UserService.class).save(userUpdated, role);

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
