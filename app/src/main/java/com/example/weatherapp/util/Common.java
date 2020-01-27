package com.example.weatherapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.weatherapp.R;
import com.example.weatherapp.view.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Common {
    //API KEY
    public final static String APP_KEY = "e5760b1073f66f63d377570783ef1e81";
    public final static String IS_FIRST_USE = "IS_FIRST_USE";
    public final static String SHARED_PREF_NAME = "WeatherPref";
    public final static String WEATHER_UNIT_CELICUS ="metric";
    public final static String DEFAULT_CITY ="London,uk";
    public final static String ICONS_BASIC_URL ="http://openweathermap.org/img/w/";
    public final static String ID_LIST = "idList";

    //config for firebase messages
    public static String content = "";
    public static String title = "";
    public static String imageUrl = "";
    public static String weatherUrl = "";


    public static boolean isConnected(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (!isConnected)
            Toast.makeText(context, R.string.no_internet_message, Toast.LENGTH_LONG).show();
        return isConnected;
    }

    //Method to format wind from degrees to direction
    public static String getFormattedWind(double degrees) {
        String windDirection = "Unknown";
        if (degrees >= 337.5 || degrees < 22.5) {
            windDirection = "N";
        } else if (degrees >= 22.5 && degrees < 67.5) {
            windDirection = "NE";
        } else if (degrees >= 67.5 && degrees < 112.5) {
            windDirection = "E";
        } else if (degrees >= 112.5 && degrees < 157.5) {
            windDirection = "SE";
        } else if (degrees >= 157.5 && degrees < 202.5) {
            windDirection = "S";
        } else if (degrees >= 202.5 && degrees < 247.5) {
            windDirection = "SW";
        } else if (degrees >= 247.5 && degrees < 292.5) {
            windDirection = "W";
        } else if (degrees >= 292.5 && degrees < 337.5) {
            windDirection = "NW";
        }

        return windDirection;
    }

    public static String convertUnixToDate(long dt) {
        Date date = new Date(dt * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.US);
        String formatted = sdf.format(date);
        return formatted;
    }

    public static String convertUnixToHour(long dt) {
        Date date = new Date(dt * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
        String formatted = sdf.format(date);
        return formatted;
    }

    public static String convertUnixToDay(long dt) {
        //Already converted
        Date date = new Date(dt);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.US);
        String formatted = sdf.format(date);
        return formatted;
    }
    public static MainActivity home;
}