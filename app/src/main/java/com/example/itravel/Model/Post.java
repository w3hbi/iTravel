package com.example.itravel.Model;

public class Post {
    private String place, latitude, longitude, image;
    public Post(){}

    public Post(String place, String latitude, String longitude, String image) {
        this.place = place;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
