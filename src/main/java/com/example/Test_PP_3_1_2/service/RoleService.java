package com.example.Test_PP_3_1_2.service;

import com.example.Test_PP_3_1_2.dao.RoleRepository;
import com.example.Test_PP_3_1_2.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(@Autowired RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public List<Role> findAll() {
     return roleRepository.findAll();
    }
    public Optional<Role> findByRolename(String rolename) {
     return roleRepository.findByRolename(rolename);
    }
    public Role save(Role role) {
     return roleRepository.save(role);
    }
    public Optional<Role> findById(Long id) {
     return roleRepository.findById(id);
    }
    public void deleteById(Long id) {
     roleRepository.deleteById(id);
    }
}
