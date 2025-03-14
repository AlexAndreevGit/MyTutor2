package com.MyTutor2.service.impl;

import com.MyTutor2.model.MyTutorUserDetails;
import com.MyTutor2.model.entity.User;
import com.MyTutor2.model.entity.UserRoleEntity;
import com.MyTutor2.model.enums.UserRoleEnum;
import com.MyTutor2.repo.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

//SpringSecurity_2 -> UserDetailsService
// we implement the interface "implements UserDetailsService" -> so we explain to spring how a user looks in our application
// on purpose no annotation, so it doesn't go in the context of spring
//userDetailsServc is reading our representation of the user and return userDetails which is the spring representation of the user
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails userDetails = userRepository
                .findByUsername(username)
                .map(u -> map(u))  //use the map(User user) method
                .orElseThrow(() -> new UsernameNotFoundException("Username with username" + username + "not found!"));   // If not such user found then throw an exception

        return userDetails;
    }


    //The spring representation of the user is described by the interface "UserDetails"
    //MyTutorUserDetails extends User which extends UserDetails -> no error
    private static UserDetails map(User user) {

        Collection<GrantedAuthority> authorities = user.getRoles().stream().map(UserRoleEntity::getRole).map(UserDetailsService::map).toList();

        MyTutorUserDetails myTutorUserDetails = new MyTutorUserDetails(   //MyTutorUserDetails has only the fields needed for UserDetails
                user.getUsername(),
                user.getPassword(),
                authorities //.map(UserRoleEntity::getRole)  for each element use the method getRole from the class UserRoleEntity

        );

        return myTutorUserDetails;


    }

    //"GrantedAuthority" is an interface with one method
    private static GrantedAuthority map(UserRoleEnum role) {

        return new SimpleGrantedAuthority(
                "ROLE_" + role
        );

    }

}
