package com.example.paindiary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.paindiary.Entity.Pain;
import com.example.paindiary.databinding.FragmentReportBinding;
import com.example.paindiary.databinding.FragmentStepTakenBinding;
import com.example.paindiary.viewmodel.PainViewModel;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class StepTakenFragment extends Fragment {

    private FragmentStepTakenBinding binding;
    private PainViewModel painViewModel;

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
        painViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PainViewModel.class);
        painViewModel.getAllPainRecord().observe(getViewLifecycleOwner(), new Observer<List<Pain>>() {
            @Override
            public void onChanged(List<Pain> pains) {
                Pain pain = pains.get(pains.size()-1);
                Float stepGoal = Float.parseFloat(pain.getStepGoal());
                Float stepTaken = Float.parseFloat(pain.getStepPhysical());
                pieChartPlot(stepGoal, stepTaken);
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

    public void pieChartPlot(Float stepGoal, Float stepTaken){
        List<PieEntry> pieEntries = new ArrayList<>();
        Float stepRemain = stepGoal - stepTaken;
        pieEntries.add(new PieEntry(stepGoal, "Step Goal Today"));
        pieEntries.add(new PieEntry(stepRemain, "Remain Steps"));
        PieDataSet set = new PieDataSet(pieEntries, "Steps Taken Pie Chart");
        List<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.COLORFUL_COLORS){
            colors.add(color);
        }
        set.setColors(colors);
        PieData data = new PieData(set);
        data.setValueTextSize(12f);
        binding.pieChartStep.setData(data);
        binding.pieChartStep.invalidate(); // refresh
    }
}