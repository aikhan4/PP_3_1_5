package com.example.Test_PP_3_1_2.initialization;

import com.example.Test_PP_3_1_2.dao.UserRepository;
import com.example.Test_PP_3_1_2.models.Role;
import com.example.Test_PP_3_1_2.models.User;
import com.example.Test_PP_3_1_2.service.RoleService;
import com.example.Test_PP_3_1_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Component
public class AppInit implements CommandLineRunner {
    private UserService userService;
    private RoleService roleService;
    private ApplicationContext applicationContext;
    private EntityManager entityManager;


    public AppInit(@Autowired UserService userService, @Autowired RoleService roleService, @Autowired ApplicationContext applicationContext, @Autowired EntityManager entityManager) {
        this.userService = userService;
        this.roleService = roleService;
        this.applicationContext = applicationContext;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userService.findAll().isEmpty()) {
            User user = new User("ayhan", "kasumov", (byte) 18, "akasum99@gmail.com", "123");
            Role roleAdmin;
            Role roleUser;
            if (roleService.findByRolename("ROLE_ADMIN").isEmpty()) {
                roleAdmin = new Role("ROLE_ADMIN");
            } else {
                roleAdmin = roleService.findByRolename("ROLE_ADMIN").get();
            }
            if (roleService.findByRolename("ROLE_USER").isEmpty()) {
                roleUser = new Role("ROLE_USER");
            } else {
                roleUser = roleService.findByRolename("ROLE_USER").get();
            }
            user.addRole(roleAdmin);
            user.addRole(roleUser);
            roleAdmin.addUser(user);
            roleUser.addUser(user);
            userService.save(user);
            System.out.println(user);
        } else {
            System.out.println(userService.findAll());
        }
        /*
        нужно чтобы при добавлении и изменении пользователя список с выбором роли был изначально раскрыт полностью
        нужно чтобы вылетали модальные окна при edit и delete пользователя
        опционально: валидация и чтобы при аутентификации пользователей их всех перенаправляло на одну и ту же html страницу, но чтоыб на ней слева на панели кнопка Админ была видна только админам (th:if)
        все стили переписать на бутстрап (или большинство). изменять стили бутстрапа с помощью чистого css если это надо
         */
    }
}
