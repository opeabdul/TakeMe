package com.example.opeyemi.takeme.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User  implements Parcelable{

    private String name;
    private String password;
    private String phoneNumber;
    private String image;

    public User(){

    }

    public User(String name, String password, String phoneNumber){
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }


}
