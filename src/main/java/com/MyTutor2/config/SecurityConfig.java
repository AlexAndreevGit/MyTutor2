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
@EnableMethodSecurity  //Enable method level security
public class SecurityConfig {

    //SpringSecurity_1 ->
    //src\main\resources\static\images\Spring_SecurityFilterChain.png
    //Creating a "SecurityFilterChain"
    //With HttpSecurity we can easily create security filer chain. It is a builder for the class "SecurityFilterChain"
    //With the fluent-API it is convenient to make the configuration
    //We cant debug the fluent-API

    @Bean    //Expose the "SecurityFilterChain" as a bean. Spring takes it and puts it as a filter in the filter chain
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(                    //Section 1 -> .authorizeHttpRequests()   setup which URL-s are available for which user
                    authorizeRequests ->                                //with the lambda we explain what is accessible and what not
                            authorizeRequests
                                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() //all static resources(CSS,images, JS) are accessible for everyone
                                    .requestMatchers("/", "users/login", "users/register").permitAll()          //accessible for all users
                                    .requestMatchers("/admin/**").hasRole("ADMIN") // Restrict access to /admin/** URLs only for users with the role ARMIN
                                    .anyRequest().authenticated()       //for all other requests, we need an authenticated user.
                )
                .formLogin(formLogin ->                               //Section 2 -> .formLogin()
                        formLogin
                                .loginPage("/users/login")     //the login page should be our custom login page
                                .usernameParameter("username") //The name of the username parameter. Same as in the login.html
                                .passwordParameter("password") //The password of the user parameter. Same as in the login.html
                                .defaultSuccessUrl("/",true)  // URL if the login is successful
                                .failureForwardUrl("/users/login-error")               // TODO client side redirect, if the login fails    failerURL- server side
                )
                .logout(                                                //Section 3 -> .logout()
                        logout ->
                                logout.logoutUrl("/users/logout")       // If we want to logout then we need to create a post request to this URL. POST because of the CSRF token. The CSRF token provide protection.
                                        .logoutSuccessUrl("/")          // where to go after successful logout
                                        .invalidateHttpSession(true)    //invalidate session after that

                )
                .build();                                               //Section 4 -> call the build-method at the end
    }

    //SpringSecurity_4 -> we are exposing "UserDetailsService" as a bean, so it is managed by spring

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new UserDetailsService(userRepository);  //we translate the users to representation which spring security understands
    }



}
