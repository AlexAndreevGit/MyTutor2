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

        MyTutorUserDetails myTutorUserDetails = new MyTutorUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(UserRoleEntity::getRole).map(UserDetailsService::map).toList()

        );

        return myTutorUserDetails;
        //.map(UserRoleEntity::getRole)  for each element use the medtod getRole from the class UserRoleEntity

    }

    //"GrantedAuthority" is an interface with one method
    private static GrantedAuthority map(UserRoleEnum role) {

        return new SimpleGrantedAuthority(
                "ROLE_" + role
        );

    }

}
