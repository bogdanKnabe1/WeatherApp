package com.example.weatherapp.view.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.weatherapp.R;
import com.example.weatherapp.model.WeatherResult;
import com.example.weatherapp.presenter.ITodayPresenter;
import com.example.weatherapp.presenter.TodayPresenter;
import com.example.weatherapp.util.Common;
import com.example.weatherapp.util.LocationUtil;
import com.example.weatherapp.view.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends Fragment implements ITodayPresenter.ViewToday {

    private TextView textTemp, textHumid, textCondition, textWind, textPressure,
            textName, textCountry, textRainPrec, textWindDeg, shareButton;

    private ImageView imageView;

    private Toolbar toolbar;
    private TextView toolbarTitle;

    //init strings
    private String name;
    private String temp;
    private String con;
    private String humidity;
    private String wind;
    private String pressure;
    private String sysCountry;
    private double windDegrees;
    private String windDirection;

    private ITodayPresenter.Actions mPresenter;
    private LocationUtil locationUtil;

    public TodayFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_today, container, false);

        //get presenter, location and context
        Common.home = (MainActivity) getActivity();
        windDirection = Common.getFormattedWind(windDegrees);
        mPresenter = new TodayPresenter(this);
        locationUtil = new LocationUtil(getActivity());

        //get data from presenter
        mPresenter.loadDataToday(getActivity(), locationUtil);

        //init toolbar
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbarTitle = getActivity().findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Today");
        writeToFirebase();

        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //find views in layout
        imageView = getView().findViewById(R.id.img_weather);
        textWindDeg = getView().findViewById(R.id.main_wind_direction);
        textRainPrec = getView().findViewById(R.id.main_precipitation);
        textCountry = getView().findViewById(R.id.main_country);
        textTemp = getView().findViewById(R.id.main_temp);
        textHumid = getView().findViewById(R.id.main_humid);
        textCondition = getView().findViewById(R.id.main_percep);
        textWind = getView().findViewById(R.id.main_wind);
        textPressure = getView().findViewById(R.id.main_pressure);
        textName = getView().findViewById(R.id.main_name);
        shareButton = getView().findViewById(R.id.button);
    }


    @Override
    public void onResume() {
        super.onResume();
        toolbarTitle.setText("Today");
    }


    @Override
    public void onStart() {
        super.onStart();
        writeToFirebase();
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWeather();
            }
        });
    }


    //Method for share weather as intent
    private void shareWeather() {
        StringBuilder stringBuilder = new StringBuilder();

        //Save data to send out
        stringBuilder.append("City is: ").append(name).append("\n");
        stringBuilder.append("Country is: ").append(sysCountry).append("\n");
        stringBuilder.append("Temperature is: ").append(temp).append("°C").append("\n");
        stringBuilder.append("Weather condition: ").append(con).append("\n");
        stringBuilder.append("Humidity is: ").append(humidity).append(" %").append("\n");
        stringBuilder.append("Pressure is: ").append(pressure).append(" hPa").append("\n");
        stringBuilder.append("Wind speed is: ").append(wind).append("km/h").append("\n");
        stringBuilder.append("Wind direction is: ").append(windDirection);

        String weather = stringBuilder.toString();

        // intent for share
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain*");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Weather forecast for today");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, weather);
        shareIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(shareIntent, "Share weather"));
    }


    //Set data to VIEWS -----------------------------------------------------

    @Override
    public void setWeatherItemsToday(WeatherResult result) {

        String icon = result.getWeather().get(0).getIcon();
        String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";
        //Loading image using Picasso
        Picasso.with(getActivity()).load(iconUrl).fit().into(imageView);

        temp = String.valueOf(result.getMain().getTemp().intValue());
        con = String.valueOf(result.getWeather().get(0).getMain());
        humidity = String.valueOf(result.getMain().getHumidity());
        wind = String.valueOf(result.getWind().getSpeed());
        pressure = String.valueOf(result.getMain().getPressure());
        name = String.valueOf(result.getName());
        sysCountry = String.valueOf(result.getSys().getCountry());
        if (result.getRain() == null) {
            textRainPrec.setText("0.0 mm");
        } else {
            String rain = String.valueOf(result.getRain().getThreeHourUpdate());
            textRainPrec.setText(rain);
        }

        //Setup data into textViews
        textWindDeg.setText(windDirection);
        textCountry.setText(", " + sysCountry);
        textTemp.setText(temp + "°C");
        textCondition.setText(" | " + con);
        textHumid.setText(humidity + " %");
        textWind.setText(wind + " km/sec");
        textPressure.setText(pressure + " hpa");
        textName.setText(name);
    }


    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
    }

    //Firebase write to realtime database --------------------------------------------------------------
    public void writeToFirebase(){
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
        myRef.setValue("HI");

    }

}
