package com.example.paindiary.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class DataViewModel extends ViewModel {

    private MutableLiveData<List<String>> weatherData;

    public DataViewModel() {
        weatherData = new MutableLiveData<>();
    }
    public LiveData<List<String>> getData(){
        return weatherData;
    }

    public void setWeatherData(List list){
        weatherData.postValue(list);
    }
}
