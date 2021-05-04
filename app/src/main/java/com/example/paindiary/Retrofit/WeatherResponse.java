package com.example.paindiary.Retrofit;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {

    @SerializedName("main")
    private Main main;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
