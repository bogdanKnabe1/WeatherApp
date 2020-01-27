package com.example.weatherapp.model;


import com.google.gson.annotations.SerializedName;

public class Sys {

    @SerializedName("country")
    private String country;
    @SerializedName("sunrise")
    private Integer sunrise;
    @SerializedName("sunset")
    private Integer sunset;
    @SerializedName("message")
    private double message;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }
    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

}
