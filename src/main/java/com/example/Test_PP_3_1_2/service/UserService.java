package com.example.Test_PP_3_1_2.service;

import com.example.Test_PP_3_1_2.dao.UserRepository;
import com.example.Test_PP_3_1_2.models.User;
import com.example.Test_PP_3_1_2.security.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    public UserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(s);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("пользователь не существует");
        }
        return new UserWrapper(user.get());
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }
    public User save(User user) {
        return userRepository.save(user);
    }
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
