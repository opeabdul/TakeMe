package com.example.opeyemi.takeme.model;

public class Category  {

    private String name;
    private String image;

    private Category(){

    }

    private Category(String name, String image){
        this.name = name;
        this.image = image;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }

}
