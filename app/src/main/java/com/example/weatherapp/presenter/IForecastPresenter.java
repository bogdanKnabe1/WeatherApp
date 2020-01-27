package com.example.weatherapp.presenter;

import android.app.Activity;

import com.example.weatherapp.model.ForecastResult;
import com.example.weatherapp.util.LocationUtil;

public interface IForecastPresenter {

    interface ViewForecast{
        void setWeatherItemsForecast(ForecastResult forecastResult);
        void showToast(String message);
    }

    interface Actions {
        void loadDataForecast(Activity activity, LocationUtil locationUtil);
        void getWeatherDataForecast(Double lat, Double lng);
        void getWeatherDataByName(String cityName);
    }
}
