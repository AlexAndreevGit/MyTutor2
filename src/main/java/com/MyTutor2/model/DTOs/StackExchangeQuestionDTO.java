package com.MyTutor2.model.DTOs;

public class StackExchangeQuestionDTO {

    private String title;

    private String url;

    private String profileName;

    private String profileImageUrl;

    private int viewCount;

    private int answerCount;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public int getViewCount() { return viewCount; }

    public void setViewCount(int viewCount) { this.viewCount = viewCount; }

    public int getAnswerCount() { return answerCount; }

    public void setAnswerCount(int answerCount) { this.answerCount = answerCount; }

}
