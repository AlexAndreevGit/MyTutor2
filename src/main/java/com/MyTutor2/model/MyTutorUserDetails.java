package com.MyTutor2.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

//SpringSecurity_7
public class MyTutorUserDetails extends User {

    public MyTutorUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {

        super(username, password, authorities);

    }

}
