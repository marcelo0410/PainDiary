package com.example.paindiary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paindiary.Retrofit.ApiClient;
import com.example.paindiary.Retrofit.ApiInterface;
import com.example.paindiary.Retrofit.WeatherResponse;
import com.example.paindiary.databinding.FragmentHomeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private String temp;
    private String humidity;
    private String pressure;
    SharedViewModel model;

    public HomeFragment(){ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        getWeather("Tokyo");

        // LiveData
        // model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        // model.setResTemperature(temp);
        binding.homeTemp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                temp = binding.homeTemp.getText().toString().substring(13);
            }
        });
        binding.homePressure.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                pressure = binding.homePressure.getText().toString().substring(10);
                Log.d("pressure", pressure);
            }
        });

        binding.homeHumidity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                humidity = binding.homeHumidity.getText().toString().substring(10);
                Log.d("humi", humidity);
            }
        });



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
                temp = response.body().getMain().getTemp();
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