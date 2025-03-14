package com.MyTutor2.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

//SpringSecurity_7 MyTutorUserDetails has only the fields needed for UserDetails
public class MyTutorUserDetails extends User {

    public MyTutorUserDetails(String username, String password, Collection<GrantedAuthority> authorities) {

        super(username, password, authorities);

    }

}
