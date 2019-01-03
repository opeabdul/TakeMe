package com.example.opeyemi.takeme.model;

public class Job {

    private String title,description, image, location, price, time, date, applicants, categoryId, userID;

    private Job(){

    }

    private Job(String title, String description, String image, String location,
                String price, String time, String date, String applicants,
                String categoryId, String userId){

        this.title = title;
        this.image = image;
        this.description = description;
        this.image = image;
        this.location = location;
        this.price = price;
        this.time = time;
        this.date = date;
        this.applicants = applicants;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getApplicants() {
        return applicants;
    }

    public void setApplicants(String applicants) {
        this.applicants = applicants;
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
