package com.example.weatherapp;

import android.app.Application;
import android.content.Context;

import com.example.weatherapp.util.NetworkManager;

public class ApplicationClass extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        NetworkManager.initialize(getApplicationContext());
    }

}