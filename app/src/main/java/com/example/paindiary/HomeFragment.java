package com.example.paindiary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.paindiary.Retrofit.ApiClient;
import com.example.paindiary.Retrofit.ApiInterface;
import com.example.paindiary.Retrofit.WeatherResponse;
import com.example.paindiary.databinding.FragmentHomeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public HomeFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        getWeather("Tokyo");
        return view;
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    private void getWeather(String name){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<WeatherResponse> call = apiInterface.getWeatherData(name);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                binding.homePressure.setText("Pressure: "+response.body().getMain().getPressure());
                binding.homeTemp.setText("Temperature: "+response.body().getMain().getTemp());
                binding.homeHumidity.setText("Humidity: "+response.body().getMain().getHumidity());
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {

            }
        });
    }

}