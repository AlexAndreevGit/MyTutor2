package com.MyTutor2.model.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Tutors")
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;


    //SpringSecurity_11 list of all roles that the user has. If the user has the admin role then he is an administrator
    @ManyToMany(fetch = FetchType.EAGER)//@ManyToMany is "lasy" by default. We change it to "eager", so the roles are fetched when we retrieve a User.
    @JoinTable(
            name = "user_roles",
            // joinColumn and inverseJoinColumn are fixed attributes of the @JoinTable
            joinColumns = @JoinColumn(name = "User_id"), //specifies the foreign key that refers to the current entity
            inverseJoinColumns = @JoinColumn(name = "UserRoleEntity_id") //specifies the foreign key that refers to the other entity
    )
    private List<UserRoleEntity> roles = new ArrayList<>();


    public User() {

    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }


    public String getName() {
        return username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
    }
}
