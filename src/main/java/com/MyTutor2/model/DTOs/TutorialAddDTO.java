package com.MyTutor2.model.DTOs;

import com.MyTutor2.model.enums.CategoryNameEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class TutorialAddDTO {

    @Size(min=2, max=40)
    @NotNull
    private String name;

    @Size(min=2, max=200)
    private String description ;

    @Positive
    @NotNull
    private Double price;

    @NotNull
    private CategoryNameEnum category ;

    public TutorialAddDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryNameEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryNameEnum category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
