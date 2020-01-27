package com.example.weatherapp.presenter;

import android.app.Activity;

import com.example.weatherapp.model.WeatherResult;
import com.example.weatherapp.util.LocationUtil;

public interface ITodayPresenter {


    interface ViewToday{
        void setWeatherItemsToday(WeatherResult result);
        void showToast(String message);
    }

    interface Actions {
        void loadDataToday(Activity activity, LocationUtil locationUtil);
        void getWeatherDataToday(Double lon, Double lat);
        void getWeatherDataByName(String cityName);
    }
}
