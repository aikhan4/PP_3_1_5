package com.example.Test_PP_3_1_2.config;

import com.example.Test_PP_3_1_2.service.UserService;
import com.example.Test_PP_3_1_2.service.UserServiceToConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserServiceToConfig userServiceToConfig;
    private CustomLoginSuccessHandler customLoginSuccessHandler;

    public SecurityConfig(@Autowired UserServiceToConfig userServiceToConfig, @Autowired CustomLoginSuccessHandler customLoginSuccessHandler) {
        this.userServiceToConfig = userServiceToConfig;
        this.customLoginSuccessHandler = customLoginSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/AdminInfo").hasRole("ADMIN")
                .antMatchers("/change").hasRole("ADMIN")
                .antMatchers("/delete").hasRole("ADMIN")
                .antMatchers("/add").hasRole("ADMIN")
                .antMatchers("/login", "/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(customLoginSuccessHandler)
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceToConfig).passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
