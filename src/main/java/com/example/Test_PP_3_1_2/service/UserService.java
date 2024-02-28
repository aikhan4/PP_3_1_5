package com.example.Test_PP_3_1_2.service;

import com.example.Test_PP_3_1_2.dao.UserRepository;
import com.example.Test_PP_3_1_2.models.Role;
import com.example.Test_PP_3_1_2.models.User;
import com.example.Test_PP_3_1_2.security.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ApplicationContext applicationContext;

    public UserService(@Autowired UserRepository userRepository, @Autowired PasswordEncoder passwordEncoder, @Autowired ApplicationContext applicationContext) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.applicationContext = applicationContext;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User saveWhenAdd(User user, String role) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (role == null) {
            Role userRole = applicationContext.getBean("roleService", RoleService.class).findById((long) 2).get();
            user.addRole(userRole);
            userRole.addUser(user);
        } else if (role.equals("ADMIN")) {
            Role adminRole = applicationContext.getBean("roleService", RoleService.class).findById((long) 1).get();
            Role userRole = applicationContext.getBean("roleService", RoleService.class).findById((long) 2).get();
            user.addRole(adminRole);
            user.addRole(userRole);
            adminRole.addUser(user);
            userRole.addUser(user);
        } else if (role.equals("USER")) {
            Role userRole = applicationContext.getBean("roleService", RoleService.class).findById((long) 2).get();
            user.addRole(userRole);
            userRole.addUser(user);
        }

        return userRepository.save(user);
    }

    public User saveWhenChange(User user, String role) {

        if (!(user.getPassword().equals(findById(user.getId()).get().getPassword()))) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (role == null) {
            Role userRole = applicationContext.getBean("roleService", RoleService.class).findById((long) 2).get();
            user.addRole(userRole);
            userRole.addUser(user);
        } else if (role.equals("ADMIN")) {
            Role adminRole = applicationContext.getBean("roleService", RoleService.class).findById((long) 1).get();
            Role userRole = applicationContext.getBean("roleService", RoleService.class).findById((long) 2).get();
            user.addRole(adminRole);
            user.addRole(userRole);
            adminRole.addUser(user);
            userRole.addUser(user);
        } else if (role.equals("USER")) {
            Role userRole = applicationContext.getBean("roleService", RoleService.class).findById((long) 2).get();
            user.addRole(userRole);
            userRole.addUser(user);
        }

        return userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}
