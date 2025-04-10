package com.MyTutor2.model.entity;

import com.MyTutor2.model.enums.UserRoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

//SpringSecurity_10
@Entity
@Table(name="roles")
public class UserRoleEntity extends BaseEntity{


    @NotNull
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    public UserRoleEntity() {
    }

    public UserRoleEntity(UserRoleEnum role) {
        this.role = role;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }
}
