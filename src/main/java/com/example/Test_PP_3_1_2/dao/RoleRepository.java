package com.example.Test_PP_3_1_2.dao;

import com.example.Test_PP_3_1_2.models.Role;
import com.example.Test_PP_3_1_2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAll();
    Optional<Role> findByRolename(String rolename);
    Role save(Role role);
    Optional<Role> findById(Long id);
    void deleteById(Long id);
}
