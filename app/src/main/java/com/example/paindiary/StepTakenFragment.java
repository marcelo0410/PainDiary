package com.example.paindiary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.paindiary.databinding.FragmentReportBinding;
import com.example.paindiary.databinding.FragmentStepTakenBinding;

import java.util.ArrayList;
import java.util.List;

public class StepTakenFragment extends Fragment {

    private FragmentStepTakenBinding binding;

    public StepTakenFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentStepTakenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        List<String> chartGenre = new ArrayList<String>();
        chartGenre.add("Pain Location Pie Chart");
        chartGenre.add("Steps Taken Pie Chart");
        chartGenre.add("Pain and Weather Line Chart");
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, chartGenre);
        binding.chartSpinner.setAdapter(spinnerAdapter);
        binding.chartSpinner.setSelection(1);
        binding.chartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setFragment(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setFragment(int id){
        Fragment fragment = null;

        switch (id){
            case 0:
                fragment = new ReportFragment();
                break;
            case 1:
                break;
            case 2:
                fragment = new PainWeatherFragment();


        }

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container_view, fragment);
            fragmentTransaction.commit();
        }
    }
}