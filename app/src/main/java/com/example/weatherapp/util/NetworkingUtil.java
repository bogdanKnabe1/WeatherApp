package com.example.weatherapp.util;

import com.example.weatherapp.retrofit.IOpenWeatherInterface;
import com.example.weatherapp.retrofit.RetrofitClient;

public class NetworkingUtil {

    private static IOpenWeatherInterface weatherService;

    public static IOpenWeatherInterface getWeatherApiInstance() {
        if (weatherService == null)
            weatherService = RetrofitClient.getClient().create(IOpenWeatherInterface.class);

        return weatherService;
    }

}
