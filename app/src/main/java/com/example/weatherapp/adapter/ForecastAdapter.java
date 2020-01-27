package com.example.weatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.ForecastResult;
import com.example.weatherapp.util.Common;
import com.squareup.picasso.Picasso;

import retrofit2.Response;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.MyViewHolder> {


    Response<ForecastResult> forecastResult;

    public ForecastAdapter(Response<ForecastResult> forecastResult) {
        this.forecastResult = forecastResult;
    }

    enum ViewTypes
    {
        HEADER, NORMAL;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_weather_forecast,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //Load icon - ICON
        /*Picasso.load(new StringBuilder("https://openweathermap.org/img/wn/")
                .append(forecastResult.body().list.get(position).weather.get(0).getIcon())
                .append(".png").toString()).into(holder.imgWeather);*/
        //DATE
        holder.tvDateTime.setText(new StringBuilder(Common.convertUnixToHour(
                forecastResult.body().list.get(position).dt)));

        //holder.txt_description.setText(new StringBuilder(forecastResult.list.get(position).weather.get(0).getDescription()));

        //TEMP
        holder.tvTemperature.setText(new StringBuilder(String.valueOf(forecastResult.body().list.get(position).main.getTemp().intValue())).append("°C"));
        //WEATHER
        holder.tvWeatherState.setText(new StringBuilder(Common.convertUnixToDate(forecastResult
                .body().list.get(position).dt)));


    }

    @Override
    public int getItemCount() {
        return forecastResult.body().list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDateTime,tvWeatherState, tvTemperature;
        ImageView imgWeather;

        public MyViewHolder( View itemView) {
            super(itemView);

            //init views
            imgWeather =itemView.findViewById(R.id.img_weather);
            tvDateTime = itemView.findViewById(R.id.txt_time);
            tvTemperature = itemView.findViewById(R.id.tvTemperature);
            //txt_description = itemView.findViewById(R.id.txt_description);
            tvWeatherState = itemView.findViewById(R.id.weatherState);

        }
    }
}