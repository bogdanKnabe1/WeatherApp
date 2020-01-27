package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class Rain {

    @SerializedName("3h")
    private Double threeHourUpdate;

    public Double getThreeHourUpdate() {
        return threeHourUpdate;
    }

    public void setThreeHourUpdate(Double threeHourUpdate) {
        this.threeHourUpdate = threeHourUpdate;
    }

}
