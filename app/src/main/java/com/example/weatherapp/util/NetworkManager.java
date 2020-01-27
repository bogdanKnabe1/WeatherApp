package com.example.weatherapp.util;

import android.content.Context;

import com.example.weatherapp.model.ForecastResult;
import com.example.weatherapp.model.WeatherResult;
import com.example.weatherapp.retrofit.IOpenWeatherInterface;
import com.example.weatherapp.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Response;

import static com.example.weatherapp.util.Common.APP_KEY;
import static com.example.weatherapp.util.Common.WEATHER_UNIT_CELICUS;

public class NetworkManager {
    private static NetworkManager uniqueInstance;
    private IOpenWeatherInterface apiInterface;

    //Can't create new instance
    private NetworkManager(Context context) {
        apiInterface = RetrofitClient.getClient().create(IOpenWeatherInterface.class);
        if (uniqueInstance != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");

    }


    public static NetworkManager getInstance() {
        if (uniqueInstance == null)
            throw new NullPointerException("Please call initialize() before getting the instance.");
        return uniqueInstance;
    }

    public synchronized static void initialize(Context applicationContext) {
        if (applicationContext == null)
            throw new NullPointerException("Provided application context is null");
        else if (uniqueInstance == null) {
            uniqueInstance = new NetworkManager(applicationContext);
        }
    }

    public void getWeatherToday(final Callback<WeatherResult> resultCallback, Double lon, Double lat) {

        Call<WeatherResult> call = apiInterface.getCurrentWeatherData(
                String.valueOf(lat),
                String.valueOf(lon),
                APP_KEY,
                "metric");

        call.enqueue(new retrofit2.Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                int responseCode = response.code();

                //between range 200...300
                if (response.isSuccessful()){
                    resultCallback.onResult(response.body());
                }else{
                    String error = String.valueOf(responseCode);
                    resultCallback.onError(error);
                }
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {
                resultCallback.onError(t.getMessage());
            }
        });
    }

    public void getWeatherByName(final Callback<WeatherResult> resultCallback, String cityName){

        Call<WeatherResult> call = apiInterface.getWeatherDataByName(
                APP_KEY,
                WEATHER_UNIT_CELICUS,
                cityName);

        call.enqueue(new retrofit2.Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                int responseCode = response.code();
                if (response.isSuccessful()){

                    resultCallback.onResult(response.body());
                }else{
                    String error = String.valueOf(responseCode);
                    resultCallback.onError(error);
                }
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {
                resultCallback.onError(String.valueOf(t));
            }
        });
    }

    public void getWeatherForecast(final Callback<ForecastResult> resultCallback, Double lat, Double lon) {

        Call<ForecastResult> call = apiInterface.getCurrentForecastData(
                String.valueOf(lat),
                String.valueOf(lon),
                APP_KEY,
                "metric");

        call.enqueue(new retrofit2.Callback<ForecastResult>() {
            @Override
            public void onResponse(Call<ForecastResult> call, Response<ForecastResult> response) {
                int responseCode = response.code();

                //between range 200...300
                if (response.isSuccessful()){
                    resultCallback.onResult(response.body());
                }else{
                    String error = String.valueOf(responseCode);
                    resultCallback.onError(error);
                }
            }

            @Override
            public void onFailure(Call<ForecastResult> call, Throwable t) {
                resultCallback.onError(t.getMessage());
            }
        });
    }

    public void getWeatherForecastByName(final Callback<ForecastResult> resultCallback, String cityName){

        Call<ForecastResult> call = apiInterface.getWeatherForecastDataByName(
                APP_KEY,
                WEATHER_UNIT_CELICUS,
                cityName);

        call.enqueue(new retrofit2.Callback<ForecastResult>() {
            @Override
            public void onResponse(Call<ForecastResult> call, Response<ForecastResult> response) {
                int responseCode = response.code();
                if (response.isSuccessful()){

                    resultCallback.onResult(response.body());
                }else{
                    String error = String.valueOf(responseCode);
                    resultCallback.onError(error);
                }
            }

            @Override
            public void onFailure(Call<ForecastResult> call, Throwable t) {
                resultCallback.onError(String.valueOf(t));
            }
        });
    }

}
