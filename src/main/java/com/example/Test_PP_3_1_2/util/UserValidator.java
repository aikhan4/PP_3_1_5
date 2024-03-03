package com.example.Test_PP_3_1_2.util;

import com.example.Test_PP_3_1_2.dto.UserDTO;
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

    public void addValidate(UserDTO userCreateTry, Errors errors) throws UsernameNotFoundException {

        Optional<User> user = userService.findByEmail(userCreateTry.getEmail());

        if ((userCreateTry.getAge() == null) || (userCreateTry.getAge() < 0) || (userCreateTry.getAge() > 127)) {
            errors.reject("age", "Некорректный возраст");
        }

        if (user.isPresent()) {
            errors.reject("email", "Пользователь с таким логином уже существуе" +
                    "т");
        }

    }

    public void changeValidate(UserDTO userChangeTry, Errors errors) throws UsernameNotFoundException {

        Optional<User> user = userService.findByEmail(userChangeTry.getEmail());

        if ((userChangeTry.getAge() == null) || (userChangeTry.getAge() < 0) || (userChangeTry.getAge() > 127)) {
            errors.reject("age", "Некорректный возраст");
        }

        if (user.isPresent()) {
            if (userChangeTry.getEmail().equals(userService.findById(userChangeTry.getId()).get().getEmail())) {

            } else {
                errors.reject("email", "Пользователь с таким логином уже существует");
            }
        }

    }

}
