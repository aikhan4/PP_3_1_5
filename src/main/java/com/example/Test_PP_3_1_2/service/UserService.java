package com.example.Test_PP_3_1_2.service;

import com.example.Test_PP_3_1_2.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void add(User user);
    List<User> getAllUsers();
    void deleteUser(Long id);
    void changeUser(Long id, String name, String surname);
    User getUser(Long id);
}
