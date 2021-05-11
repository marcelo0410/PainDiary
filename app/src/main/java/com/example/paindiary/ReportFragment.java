package com.example.paindiary;

import android.os.Build;
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
import android.widget.Toast;

import com.example.paindiary.Entity.Pain;
import com.example.paindiary.databinding.FragmentReportBinding;
import com.example.paindiary.viewmodel.PainViewModel;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReportFragment extends Fragment {

    private FragmentReportBinding binding;
    private PainViewModel painViewModel;

    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentReportBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        List<String> chartGenre = new ArrayList<String>();
        chartGenre.add("Pain Location Pie Chart");
        chartGenre.add("Steps Taken Pie Chart");
        chartGenre.add("Pain and Weather Line Chart");
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, chartGenre);
        binding.chartSpinner.setAdapter(spinnerAdapter);
        binding.chartSpinner.setSelection(0);
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
                pieChartPlot(pains);
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
                break;
            case 1:
                fragment = new StepTakenFragment();
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

    //TODO Percentage sign
    // hardcode data to check percentage sign
    public void pieChartPlot(List<Pain> pains){
        List<PieEntry> pieEntries = new ArrayList<>();
        Map<String, Integer> locationMap = new HashMap<String, Integer>();
        for (Pain pain : pains){
            if (!locationMap.containsKey(pain.getLocation())){
                locationMap.put(pain.getLocation(), 1);
            } else {
                Integer count = (Integer) locationMap.get(pain.getLocation());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    locationMap.replace(pain.getLocation(), count+1);
                }
            }
        }
        //Toast.makeText(getActivity(), String.valueOf(locationMap.size()), Toast.LENGTH_LONG).show();
        int painCount = pains.size();
        Map<String, Float> painPercent = new HashMap<>();
        for (Map.Entry<String, Integer> entry : locationMap.entrySet()){
            int value = entry.getValue();
            float tempF = (float) (Math.round((float)value/painCount*100));
            //painPercent.put(entry.getKey(), tempF);
            tempF = Float.parseFloat(String.valueOf(tempF) + "f");
            pieEntries.add(new PieEntry(tempF, entry.getKey()));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Pain Location Pie Chart");
        List<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.COLORFUL_COLORS){
            colors.add(color);
        }
        pieDataSet.setColors(colors);
        PieData data = new PieData(pieDataSet);
        data.setValueFormatter(new PercentFormatter());
        binding.pieChart.setUsePercentValues(true);
        binding.pieChart.setDrawEntryLabels(true);
        binding.pieChart.setData(data);
        binding.pieChart.invalidate();

    }

}