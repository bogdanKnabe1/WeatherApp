package com.example.weatherapp.presenter;

import android.app.Activity;

import com.example.weatherapp.base.BasePresenter;
import com.example.weatherapp.model.ForecastResult;
import com.example.weatherapp.util.Callback;
import com.example.weatherapp.util.GenericListener;
import com.example.weatherapp.util.LocationUtil;
import com.example.weatherapp.util.NetworkManager;
import com.example.weatherapp.util.PermissionUtil;

import static com.example.weatherapp.util.Common.DEFAULT_CITY;

public class ForecastPresenter extends BasePresenter implements IForecastPresenter.Actions {

    //View
    private IForecastPresenter.ViewForecast mView;


    public ForecastPresenter(IForecastPresenter.ViewForecast mView) {
        this.mView = mView;
    }

    //when permission is granted, getting location and starting request
    @Override
    public void loadDataForecast(final Activity activity, final LocationUtil locationUtil) {
        PermissionUtil.askForLocationPermission(activity);

        if (!locationUtil.canGetLocation()) {
            locationUtil.showSettingsAlert(new GenericListener() {
                @Override
                public void onSuccess() {
                    if (PermissionUtil.isLocationPermissionGranted(activity) && locationUtil.canGetLocation() &&
                            locationUtil.getLatitude() != 0.0 && locationUtil.getLongitude() != 0.0) {
                        getWeatherDataForecast(locationUtil.getLatitude(), locationUtil.getLongitude());
                    }
                }

                @Override
                public void onFailure() {
                    getWeatherDataByName(DEFAULT_CITY);
                }
            });

         //if we can get location and permission is granted
        } else {
            if (locationUtil.canGetLocation() &&
                    locationUtil.getLatitude() != 0.0 && locationUtil.getLongitude() != 0.0)
                getWeatherDataForecast(locationUtil.getLatitude(), locationUtil.getLongitude());
            else
                getWeatherDataByName(DEFAULT_CITY);
        }
    }

    //delegate data to the VIEW from network manager for weather forecast
    @Override
    public void getWeatherDataForecast(Double lat, Double lng) {
        NetworkManager.getInstance().getWeatherForecast(new Callback<ForecastResult>() {
            @Override
            public void onResult(ForecastResult result) {
                mView.setWeatherItemsForecast(result);
            }

            @Override
            public void onError(String message) {
                mView.showToast(message);
            }
        }, lat, lng);
    }

    @Override
    public void getWeatherDataByName(String cityName) {
        NetworkManager.getInstance().getWeatherForecastByName(new Callback<ForecastResult>() {
            @Override
            public void onResult(ForecastResult result) {
                mView.setWeatherItemsForecast(result);
            }

            @Override
            public void onError(String message) {
                mView.showToast(message);
            }
        }, cityName);
    }


}
