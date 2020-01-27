package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("speed")
    private Double speed;
    @SerializedName("deg")
    private Double deg;

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getDegrees() {
        return deg;
    }

    public void setDegrees(Double deg) {
        this.deg = deg;
    }

}