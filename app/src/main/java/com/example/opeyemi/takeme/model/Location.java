package com.example.opeyemi.takeme.model;

import java.io.Serializable;

public class Location  implements Serializable {
    private String address;
    private String area;
    private String city;
    private String state;

    public Location() {

    }

    public Location(String address, String area, String city, String state) {
        this.address = address;
        this.area = area;
        this.city = city;
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}