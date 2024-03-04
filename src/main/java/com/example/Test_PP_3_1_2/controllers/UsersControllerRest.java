package com.example.Test_PP_3_1_2.controllers;

import com.example.Test_PP_3_1_2.dto.UserDTO;
import com.example.Test_PP_3_1_2.models.User;
import com.example.Test_PP_3_1_2.service.UserService;
import com.example.Test_PP_3_1_2.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UsersControllerRest {
    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public UsersControllerRest(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/getCurrentUser")
    public ResponseEntity<User> getCurrentUser(Principal principal) {

        return new ResponseEntity<>(userService.findByEmail(principal.getName()).get(), HttpStatus.OK);

    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getAllUsers() {

        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);

    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {

        return new ResponseEntity<>(userService.findById(id).get(), HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long id) {

        userService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO, BindingResult bindingResult) {

        userValidator.addValidate(userDTO, bindingResult);

        if (bindingResult.hasErrors()) {

            Map<String, String> errors = new HashMap<>();

            List<ObjectError> globalErrors = bindingResult.getGlobalErrors();

            for (ObjectError error : globalErrors) {
                String errorName = error.getCode(); // Имя ошибки
                String errorMessage = error.getDefaultMessage(); // Сообщение об ошибке
                errors.put(errorName, errorMessage);
            }

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        userService.saveWhenAdd(userDTO, userDTO.getRole());

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PatchMapping("/change")
    public ResponseEntity<?> changeUser(@RequestBody UserDTO userDTO, BindingResult bindingResult) {

        userValidator.changeValidate(userDTO, bindingResult);

        if (bindingResult.hasErrors()) {

            Map<String, String> errors = new HashMap<>();

            List<ObjectError> globalErrors = bindingResult.getGlobalErrors();

            for (ObjectError error : globalErrors) {
                String errorName = error.getCode(); // Имя ошибки
                String errorMessage = error.getDefaultMessage(); // Сообщение об ошибке
                errors.put(errorName, errorMessage);
            }

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        userService.saveWhenChange(userDTO, userDTO.getRole());

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("test")
    public void testDebugMethod(@ModelAttribute User user, BindingResult bindingResult) {
        bindingResult.reject("name", "name message");
        bindingResult.reject("email", "email message");

        Map<String, String> errors = new HashMap<>();

        List<ObjectError> globalErrors = bindingResult.getGlobalErrors();

        for (ObjectError error : globalErrors) {
            String errorName = error.getCode(); // Имя ошибки
            String errorMessage = error.getDefaultMessage(); // Сообщение об ошибке
            errors.put(errorName, errorMessage);
        }

        System.out.println(errors);
    }

}
