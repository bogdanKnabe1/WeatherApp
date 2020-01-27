package com.example.weatherapp.adapter;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.HeaderItem;
import com.example.weatherapp.model.MyList;
import com.example.weatherapp.retrofit.ListItem;
import com.example.weatherapp.util.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter{

    List<ListItem> dataModels;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();



    public static final int HEADER_VIEW_TYPE = 1;
    public static final int SUBHEADER_VIEW_TYPE = 2;


    public  RecyclerViewAdapter(List<ListItem> dataModels)
    {
        this.dataModels = dataModels;

    }

    //ViewHolder for header
    class HeaderViewHolder extends RecyclerView.ViewHolder{
        TextView tvDay;
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
        }
    }

    //ViewHolder for item
    class SubHeaderViewHolder extends RecyclerView.ViewHolder{

        TextView weatherState, timeState, tempState;
        ImageView weatherIcon;

        public SubHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            weatherState = itemView.findViewById(R.id.weatherState);
            timeState = itemView.findViewById(R.id.txt_time);
            tempState = itemView.findViewById(R.id.tvTemperature);
            weatherIcon = itemView.findViewById(R.id.img_weather);


        }
    }


    //inflate our layouts for each type
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        switch (viewType){
            case HEADER_VIEW_TYPE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_day, viewGroup, false);
                return new HeaderViewHolder(view);
            case SUBHEADER_VIEW_TYPE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_weather_forecast, viewGroup, false);
                return new SubHeaderViewHolder(view) ;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        switch (dataModels.get(position).getViewType()) {
            //Setup data to views in recycler
                //Header
            case HEADER_VIEW_TYPE:
                HeaderViewHolder headerViewHolder=(HeaderViewHolder) viewHolder;
                HeaderItem headerItem = (HeaderItem) dataModels.get(position);
                headerViewHolder.tvDay.setText(headerItem.getWeatherTitle());
                break;
            case SUBHEADER_VIEW_TYPE:
                //item
                final SubHeaderViewHolder subHeaderViewHolder =(SubHeaderViewHolder) viewHolder;
                MyList myList = (MyList) dataModels.get(position);
                subHeaderViewHolder.weatherState.setText(myList.weather.get(0).getDescription());
                subHeaderViewHolder.timeState.setText(Common.convertUnixToHour(myList.dt));
                subHeaderViewHolder.tempState.setText(new StringBuilder(String.valueOf(myList.main.getTemp().intValue())).append("°C"));

                //Setup image
                Picasso.with(((SubHeaderViewHolder) viewHolder).weatherIcon.getContext())
                        .load(new StringBuilder("https://openweathermap.org/img/wn/")
                        .append(myList.weather.get(0).getIcon())
                        .append(".png").toString())
                        .fit()
                        .into(((SubHeaderViewHolder) viewHolder).weatherIcon);
                break;


        }
    }

    //Size of LIST
    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    //Method for getting ViewType in our list
    @Override
    public int getItemViewType(int position) {
      return dataModels.get(position).getViewType();
    }
}