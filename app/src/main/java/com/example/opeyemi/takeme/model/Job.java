package com.example.opeyemi.takeme.model;

import java.io.Serializable;

public class Job implements Serializable {

    private String id, title,description, image, price, timestamp, categoryId, userID;
    private Location location;
    private Job(){

    }

    public Job(String title, String description, String image, Location location,
                String price, String timestamp,
                String categoryId, String userId){

        this.title = title;
        this.image = image;
        this.description = description;
        this.image = image;
        this.location = location;
        this.price = price;
        this.timestamp = timestamp;
        this.categoryId = categoryId;
        this.userID = userId;
    }

    public Job(String title, String image){
        this.title = title;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
