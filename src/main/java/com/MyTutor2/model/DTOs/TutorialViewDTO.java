package com.MyTutor2.model.DTOs;

public class TutorialViewDTO {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private String emailOfTheTutor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmailOfTheTutor() {
        return emailOfTheTutor;
    }

    public void setEmailOfTheTutor(String emailOfTheTutor) {
        this.emailOfTheTutor = emailOfTheTutor;
    }
}
