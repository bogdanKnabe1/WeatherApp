package com.example.weatherapp.model;

import com.example.weatherapp.adapter.RecyclerViewAdapter;
import com.example.weatherapp.retrofit.ListItem;

import java.util.List;

public class MyList extends ListItem{

    public int dt;
    public Main main;
    public List<Weather> weather;
    public Clouds clouds;
    public Wind wind;
    public Rain rain;
    public Sys sys;
    public String dt_txt;

    @Override
    public int getViewType() {
        return RecyclerViewAdapter.SUBHEADER_VIEW_TYPE;
    }

}

