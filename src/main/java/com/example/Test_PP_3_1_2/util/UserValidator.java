package com.example.Test_PP_3_1_2.util;

import com.example.Test_PP_3_1_2.models.User;
import com.example.Test_PP_3_1_2.security.UserWrapper;
import com.example.Test_PP_3_1_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserValidator {
    private final UserService userService;

    public UserValidator(@Autowired UserService userService) {
        this.userService = userService;
    }

    public void addValidate(User authTryUser, Errors errors) throws UsernameNotFoundException {

        Optional<User> user = userService.findByEmail(authTryUser.getEmail());

        if ((authTryUser.getAge() == null) || (authTryUser.getAge() < 0) || (authTryUser.getAge() > 127)) {
            errors.rejectValue("age", "Некорректный возраст");
        }

        if (user.isPresent()) {
            errors.rejectValue("email", "Пользователь с таким логином уже существует");
        }

    }

    public void changeValidate(User authTryUser, Errors errors) throws UsernameNotFoundException {

        Optional<User> user = userService.findByEmail(authTryUser.getEmail());

        if ((authTryUser.getAge() == null) || (authTryUser.getAge() < 0) || (authTryUser.getAge() > 127)) {
            errors.rejectValue("age", "Некорректный возраст");
        }

        if (user.isPresent()) {
            if (authTryUser.getEmail().equals(userService.findById(authTryUser.getId()).get().getEmail())) {

            } else {
                errors.rejectValue("email", "Пользователь с таким логином уже существует");
            }
        }

    }

}
