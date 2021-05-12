package com.example.paindiary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
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
    private final String[] weatherDataList;


    public HomeFragment(){
        weatherDataList = new String[3];
    }

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
                String temp = binding.homeTemp.getText().toString().substring(13);
                //weatherDataList[0] = temp;
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
                String humidity = binding.homeHumidity.getText().toString().substring(10);
                //weatherDataList[1] = humidity;
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
                String pressure = binding.homePressure.getText().toString().substring(10);
                //weatherDataList[2] = pressure;
            }
        });



        // transfer weatherData
        // communicateDataFragment();
        // Toast.makeText(requireActivity(), array[0], Toast.LENGTH_LONG).show();

        return view;
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    private void getWeather(String name){
        final String[] weatherArray = new String[3];
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<WeatherResponse> call = apiInterface.getWeatherData(name);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                // TODO extract three weather data
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