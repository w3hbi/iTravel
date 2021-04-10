package com.example.itravel.Model;

public class User {

    public String username, email, phone, image_url;

    public User(){

    }

    public User(String username, String email, String phone, String image_url) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.image_url = image_url;
    }
}
