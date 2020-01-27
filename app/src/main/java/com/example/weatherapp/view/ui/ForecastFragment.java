package com.example.weatherapp.view.ui;

import android.animation.Animator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.adapter.RecyclerViewAdapter;
import com.example.weatherapp.model.ForecastResult;
import com.example.weatherapp.model.HeaderItem;
import com.example.weatherapp.model.MyList;
import com.example.weatherapp.presenter.ForecastPresenter;
import com.example.weatherapp.presenter.IForecastPresenter;
import com.example.weatherapp.retrofit.ListItem;
import com.example.weatherapp.util.Common;
import com.example.weatherapp.util.LocationUtil;
import com.example.weatherapp.view.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ForecastFragment extends Fragment implements IForecastPresenter.ViewForecast {

    private Toolbar toolbar;
    private TextView toolbarTitle;

    private TextView tvTime,tvWeatherState, tvTemperature;
    private RecyclerView recyclerForecast;

    private IForecastPresenter.Actions mPresenter;
    private LocationUtil locationUtil;
    private Animator spruceAnimator;


    public ForecastFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_forecast, container, false);

        //init presenter, location
        Common.home = (MainActivity) getActivity();
        mPresenter = new ForecastPresenter(this);
        locationUtil = new LocationUtil(getActivity());

        //load data from presenter
        mPresenter.loadDataForecast(getActivity(), locationUtil);

        //init toolbar
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbarTitle = getActivity().findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Forecast");

        //Init text views
        tvTime = v.findViewById(R.id.txt_time);
        tvTemperature = v.findViewById(R.id.tvTemperature);
        tvWeatherState = v.findViewById(R.id.weatherState);


        //init recyclerview
        recyclerForecast = v.findViewById(R.id.recycler_forecast);
        recyclerForecast.setHasFixedSize(true);
        recyclerForecast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        toolbarTitle.setText("Forecast");
    }


    @Override
    public void setWeatherItemsForecast(ForecastResult forecastResult) {
        //PLACE FOR MERGE FORECAST + HEADER

        //Sort list
        Comparator<MyList> comparator = new Comparator<MyList>() {
            @Override
            public int compare(MyList o1, MyList o2) {
                return o1.dt - o2.dt;
            }
        };

        // New list witch one we transmit to adapter
        List<ListItem> newWeatherList = new ArrayList<>();

        //Header Item
        HeaderItem headerItem = new HeaderItem();
        //Set first item as header today
        headerItem.setWeatherTitle("Today");
        newWeatherList.add(headerItem);

        //Adding to new list of forecast result --> MyList our list of weather objects and using sort on this list
        List<MyList> forecastResultsList = new ArrayList<>(Objects.requireNonNull(forecastResult.list));
        Collections.sort(forecastResultsList, comparator);


        //Loop for manage current data day and find the -
        //- limit value of the end of the day, in order to insert a header with this day in this list
        for (int i = 1; i < forecastResultsList.size() - 1; i++){
            //i elements in list
            Calendar firstValue = Calendar.getInstance();
            firstValue.setTimeInMillis(forecastResultsList.get(i).dt * 1000L);
            //i - 1 elements in list
            Calendar secondValue = Calendar.getInstance();
            secondValue.setTimeInMillis(forecastResultsList.get(i - 1).dt * 1000L);
            //compare 2 elements and if their data != adding header item
            if (secondValue.get(Calendar.DAY_OF_MONTH) != firstValue.get(Calendar.DAY_OF_MONTH)){
                HeaderItem head = new HeaderItem();
                head.setWeatherTitle(Common.convertUnixToDay(firstValue.getTimeInMillis()));
                newWeatherList.add(forecastResultsList.get(i - 1));
                newWeatherList.add(head);
            }else {
                newWeatherList.add(forecastResultsList.get(i - 1));
            }
        }
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(newWeatherList);
        recyclerForecast.setAdapter(adapter);
    }


    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
    }
}