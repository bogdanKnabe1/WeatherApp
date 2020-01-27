package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class Coord {

    @SerializedName("lon")
    private Double lon;
    @SerializedName("lat")
    private Double lat;

    public Double getLongitude()
    {
        return lon;
    }

    public void setLongitude(Double lon) {
        this.lon = lon;
    }

    public Double getLatitude() {
        return lat;
    }

    public void setLatitude(Double lat) {
        this.lat = lat;
    }
}