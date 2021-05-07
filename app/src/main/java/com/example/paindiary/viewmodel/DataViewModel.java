package com.example.paindiary.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class DataViewModel extends ViewModel {

    private MutableLiveData<List<String>> weatherData;

    public MutableLiveData<List<String>> getData(){
        if (weatherData == null) {
            weatherData = new MutableLiveData<List<String>>();
        }
        return weatherData;
    }
}
