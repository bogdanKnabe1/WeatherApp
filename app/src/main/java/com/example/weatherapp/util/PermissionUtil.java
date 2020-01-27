package com.example.weatherapp.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class PermissionUtil {
    private static final int MY_PERMISSION_REQUEST_CODE = 200;


    public static void askForLocationPermission(Activity activity) {
        if (!isLocationPermissionGranted(activity)) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(activity, ACCESS_COARSE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSION_REQUEST_CODE);
            }
        }
    }

    public static boolean isLocationPermissionGranted(Activity activity){
        if ((ContextCompat.checkSelfPermission(activity, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(activity, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            return false;
        }
        return true;
    }

}