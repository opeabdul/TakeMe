package com.example.opeyemi.takeme.model;

public class Job {

    private String title,description, image, price, day, month, categoryId, userID;
    private Location location;
    private Job(){

    }

    public Job(String title, String description, String image, Location location,
                String price, String day, String month,
                String categoryId, String userId){

        this.title = title;
        this.image = image;
        this.description = description;
        this.image = image;
        this.location = location;
        this.price = price;
        this.day = day;
        this.month = month;
        this.categoryId = categoryId;
        this.userID = userId;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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
