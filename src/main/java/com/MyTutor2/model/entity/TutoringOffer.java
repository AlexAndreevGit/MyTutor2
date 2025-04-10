package com.MyTutor2.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "tutoringOffer")
public class TutoringOffer extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    @Positive
    private Double price;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User addedBy;

    public TutoringOffer(String name, String description, Double price, Category category, User addedBy) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.addedBy = addedBy;
    }

    public TutoringOffer() {

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }
}
