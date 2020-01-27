package com.example.weatherapp.util;

//Custom callback for return result in presenter
public interface Callback<T> {
    void onResult(T result);
    void onError(String message);
}
