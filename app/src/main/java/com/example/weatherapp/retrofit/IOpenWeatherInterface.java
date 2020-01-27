package com.example.weatherapp.retrofit;

import com.example.weatherapp.model.ForecastResult;
import com.example.weatherapp.model.WeatherResult;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherInterface {

    //  Query params below
    //  full link http://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=e5760b1073f66f63d377570783ef1e81
    //  @GET("weather?lat=35&lon=139&appid=e5760b1073f66f63d377570783ef1e81")

    //GET request for today weather by city name
    @GET("weather")
    Call<WeatherResult> getWeatherDataByName(@Query("APPID") String weatherApiKey,
                                                   @Query("units") String unit,
                                                   @Query("q") String cityName);
    //GET request for today weather by lat and lng
    @GET("weather")
    Call<WeatherResult> getCurrentWeatherData(@Query("lat") String lat,
                                              @Query("lon") String lon,
                                              @Query("appid") String app_id,
                                              @Query("units") String metric);
    //GET request for 5 days by lat and lng
    @GET("forecast")
    Call<ForecastResult> getCurrentForecastData(@Query("lat") String lat,
                                                @Query("lon") String lon,
                                                @Query("appid") String app_id,
                                                @Query("units") String metric);
    //GET request for 5 days by name of city
    @GET("forecast")
    Call<ForecastResult> getWeatherForecastDataByName(@Query("APPID") String weatherApiKey,
                                                     @Query("units") String unit,
                                                     @Query("q") String cityName);
}



