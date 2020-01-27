package com.example.weatherapp.model;

import com.example.weatherapp.adapter.RecyclerViewAdapter;
import com.example.weatherapp.retrofit.ListItem;


public class HeaderItem extends ListItem {

    String weatherTitle;

    public String getWeatherTitle() {
        return weatherTitle;
    }

    public void setWeatherTitle(String weatherTitle) {
        this.weatherTitle = weatherTitle;
    }

    @Override
    public int getViewType() {
        return RecyclerViewAdapter.HEADER_VIEW_TYPE;
    }

}


