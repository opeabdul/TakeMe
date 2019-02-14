package com.example.opeyemi.takeme.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class User  implements Serializable {

    private String name;
    private String password;
    private String phoneNumber;
    private String image;
    private String notificationKey;

    public User(){

    }

    public User(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public User(String name, String password, String phoneNumber){
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getNotificationKey() {
        return notificationKey;
    }

    public void setNotificationKey(String notificationKey) {
        this.notificationKey = notificationKey;
    }

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }




}
