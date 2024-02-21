package com.example.Test_PP_3_1_2.dao;

import com.example.Test_PP_3_1_2.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserDao {
    void add(User user);
    List<User> getAllUsers();
    void deleteUser(Long id);
    void changeUser(Long id, String name, String surname);
    User getUser(Long id);

}