package com.example.paindiary;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<String> userEmail;
    private MutableLiveData<String> resTemperature;
    private MutableLiveData<String> resHumidity;
    private MutableLiveData<String> resPressure;

    public SharedViewModel() {
        userEmail = new MutableLiveData<>();
        resTemperature = new MutableLiveData<>();
        resHumidity = new MutableLiveData<>();
        resPressure = new MutableLiveData<>();
    }

    public LiveData<String> getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String email) {
        userEmail.setValue(email);
    }

    public LiveData<String> getResTemperature() {
        return resTemperature;
    }

    public void setResTemperature(String temp) {
        resTemperature.setValue(temp);
    }

    public LiveData<String> getResHumidity(){
        return resHumidity;
    }

    public void setResHumidity(String humidity){
        resHumidity.setValue(humidity);
    }

    public LiveData<String> getResPressure(){
        return resPressure;
    }

    public void setResPressure(String pressure){
        resPressure.setValue(pressure);
    }
}
