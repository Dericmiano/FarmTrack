package com.example.farmtrack.model;

public class Tips {
    private String topic;
    private String title;
    private String description;
    private String imageUrl;
    private String date;

    public Tips() {
    }

    public Tips(String topic, String title, String description, String imageUrl, String date) {
        this.topic = topic;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.date = date;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
