package com.MyTutor2.config;

import com.MyTutor2.repo.UserRepository;

import com.MyTutor2.service.impl.UserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity  //Enable method level security   ->   .requestMatchers("/admin/**").hasRole("ADMIN")
public class SecurityConfig {

    //SpringSecurity_1 ->
    //With HttpSecurity we can easily create security filer chain. It is a builder for the class "SecurityFilterChain"
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                    authorizeRequests ->
                            authorizeRequests
                                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                    .requestMatchers("/","/home", "/about-us", "users/login","/users/login-error", "users/register","/api/convert").permitAll()
                                    .requestMatchers("/admin/**").hasRole("ADMIN")
                                    .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/users/login")     //the login page should be our custom login page
                                .usernameParameter("username") //The name of the username parameter. Same as in the login.html
                                .passwordParameter("password")
                                .defaultSuccessUrl("/home",true)
                                .failureUrl("/users/login-error")
                )
                .logout(
                        logout ->
                                logout.logoutUrl("/users/logout")       // If we want to logout then we need to create a post request to this URL.
                                        .logoutSuccessUrl("/")
                                        .invalidateHttpSession(true)

                )
                .build();
    }

    //SpringSecurity_4 -> we are exposing "UserDetailsService" as a bean, so it is managed by spring

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new UserDetailsService(userRepository);  //we translate the users to representation that spring security understands
    }



}
