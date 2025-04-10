package com.MyTutor2.model.entity;

import com.MyTutor2.model.enums.CategoryNameEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "TutoringCategory")
public class Category extends BaseEntity {

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private CategoryNameEnum name;

    @Column
    private String description;


    public Category(CategoryNameEnum name){
        this.name=name;
    }

    public Category() {
    }


    public void setName(CategoryNameEnum name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
