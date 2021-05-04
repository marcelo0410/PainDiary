package com.example.paindiary.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    // http://api.openweathermap.org/data/2.5/weather?id=524901&lang=fr&appid={API key}
    @GET("weather?&appid=699a3d38fa781cf222e8eccc6efffe26&units=metric")
    Call<WeatherResponse> getWeatherData(@Query("q") String name);
}
