package com.example.weatherapp.presenter;

import android.app.Activity;

import com.example.weatherapp.base.BasePresenter;
import com.example.weatherapp.model.WeatherResult;
import com.example.weatherapp.util.Callback;
import com.example.weatherapp.util.GenericListener;
import com.example.weatherapp.util.LocationUtil;
import com.example.weatherapp.util.NetworkManager;
import com.example.weatherapp.util.PermissionUtil;

import static com.example.weatherapp.util.Common.DEFAULT_CITY;

public class TodayPresenter extends BasePresenter implements ITodayPresenter.Actions{

    //View
    private ITodayPresenter.ViewToday mView;

    //init
    public TodayPresenter(ITodayPresenter.ViewToday mView) {
        this.mView = mView;
    }

    //when permission is granted, getting location and starting request
    @Override
    public void loadDataToday(final Activity activity, final LocationUtil locationUtil) {
        PermissionUtil.askForLocationPermission(activity);

        //IF we can't get location
        if (!locationUtil.canGetLocation()) {
            locationUtil.showSettingsAlert(new GenericListener() {
                @Override
                public void onSuccess() {
                    if (PermissionUtil.isLocationPermissionGranted(activity) && locationUtil.canGetLocation() &&
                            locationUtil.getLatitude() != 0.0 && locationUtil.getLongitude() != 0.0) {
                            getWeatherDataToday(locationUtil.getLongitude(), locationUtil.getLatitude());
                    }
                }

                @Override
                public void onFailure() {
                    getWeatherDataByName(DEFAULT_CITY);
                }
            });

        //IF we have permission and we can get location
        } else {
            if (locationUtil.canGetLocation() && locationUtil.getLatitude() != 0.0 && locationUtil.getLongitude() != 0.0)
                getWeatherDataToday(locationUtil.getLongitude(), locationUtil.getLatitude());
            else
                getWeatherDataByName(DEFAULT_CITY);
        }
    }

    //getting result of REQUEST for today cast with current location
    public void getWeatherDataToday(Double lon, Double lat) {
        NetworkManager.getInstance().getWeatherToday(new Callback<WeatherResult>() {
            @Override
            public void onResult(WeatherResult result) {
                mView.setWeatherItemsToday(result);
            }

            @Override
            public void onError(String message) {
                mView.showToast(message);
            }
        }, lon, lat);
    }

    //getting result of REQUEST for today cast with out location
    @Override
    public void getWeatherDataByName(String cityName) {
        NetworkManager.getInstance().getWeatherByName(new Callback<WeatherResult>() {
            @Override
            public void onResult(WeatherResult result) {
                mView.setWeatherItemsToday(result);
            }

            @Override
            public void onError(String message) {
                mView.showToast(message);
            }
        }, cityName);
    }
}
