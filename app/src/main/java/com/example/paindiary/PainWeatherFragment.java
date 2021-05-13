package com.example.paindiary;

import android.app.DatePickerDialog;
import android.graphics.Color;
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
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.paindiary.Entity.Pain;
import com.example.paindiary.databinding.FragmentPainWeatherBinding;
import com.example.paindiary.viewmodel.PainViewModel;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;


public class PainWeatherFragment extends Fragment {

    private FragmentPainWeatherBinding binding;
    private PainViewModel painViewModel;
    private String startDate;
    private String endDate;
    private String pValue;
    private String correValue;

    public PainWeatherFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPainWeatherBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Chart Spinner
        List<String> chartGenre = new ArrayList<String>();
        chartGenre.add("Pain Location Pie Chart");
        chartGenre.add("Steps Taken Pie Chart");
        chartGenre.add("Pain and Weather Line Chart");
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, chartGenre);
        binding.chartSpinner.setAdapter(spinnerAdapter);
        binding.chartSpinner.setSelection(2);
        binding.chartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setFragment(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Weather spinner
        List<String> weatherGenre = new ArrayList<String>();
        weatherGenre.add("Temperature");
        weatherGenre.add("Humidity");
        weatherGenre.add("Pressure");
        final ArrayAdapter<String> weatherSpinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, weatherGenre);
        binding.weatherSpinner.setAdapter(weatherSpinnerAdapter);
        binding.weatherSpinner.setSelection(0);


        painViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PainViewModel.class);



        // datePicker
        binding.buttonTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = day + "/" + (month+1) + "/" + year;
                        binding.etStartDate.setText(format);
                    }
                }, year,month, day).show();
            }
        });



        binding.ButtonTimeEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = day + "/" + (month+1) + "/" + year;
                        binding.etEndDate.setText(format);
                    }
                }, year,month, day).show();
            }
        });

        binding.buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDate = binding.etStartDate.getText().toString();
                endDate = binding.etEndDate.getText().toString();
                if (!dateValidation()){
                    Toast.makeText(getContext(), "Start date is after end date!", Toast.LENGTH_LONG).show();
                    return;
                }

                String spinnerItemSelected = binding.weatherSpinner.getSelectedItem().toString();
                painViewModel.getAllPainRecord().observe(getViewLifecycleOwner(), new Observer<List<Pain>>() {
                    @Override
                    public void onChanged(List<Pain> pains) {

                        // Extract pain data by userInput
                        List<Pain> painSelectedList = new ArrayList<>();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date startDateFormat = null;
                        Date endDateFormat= null;
                        try {
                            startDateFormat = sdf.parse(startDate);
                            endDateFormat = sdf.parse(endDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        for (Pain pain : pains){
                            Date tempDate = null;
                            try {
                                tempDate = sdf.parse(pain.getDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (!startDateFormat.after(tempDate) && !endDateFormat.before(tempDate)){
                                painSelectedList.add(pain);
                            }
                        }

                        // sort list by date
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            painSelectedList.sort((o1,o2) -> {
                                try {
                                    return sdf.parse(o1.getDate()).compareTo(sdf.parse(o2.getDate()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                return 0;
                            });
                        }
                        List<Entry> painEntryList = new ArrayList<>();
                        List<Entry> weatherEntryList = new ArrayList<>();
                        final ArrayList<String> xLabelList = new ArrayList<>();
                        for (Pain pain : painSelectedList){
                            painEntryList.add(new Entry(Float.parseFloat(pain.getDate().substring(0,2)), Float.parseFloat(pain.getLevel())));
                            if (spinnerItemSelected.equals("Temperature")){
                                weatherEntryList.add(new Entry(Float.parseFloat(pain.getDate().substring(0,2)), Float.parseFloat(pain.getTemperature())));
                                xLabelList.add(pain.getDate());
                            } else if (spinnerItemSelected.equals("Humidity")){
                                weatherEntryList.add(new Entry(Float.parseFloat(pain.getDate().substring(0,2)), Float.parseFloat(pain.getHumidity())));
                                xLabelList.add(pain.getDate());
                            } else {
                                weatherEntryList.add(new Entry(Float.parseFloat(pain.getDate().substring(0,2)), Float.parseFloat(pain.getPressure())));
                                xLabelList.add(pain.getDate());
                            }
                        }

                        LineDataSet painLineDataSet = new LineDataSet(painEntryList,"Weather");
                        LineDataSet weatherLineDataSet = new LineDataSet(weatherEntryList, "Pain Level");

                        painLineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                        weatherLineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
                        painLineDataSet.setColor(Color.BLUE);
                        weatherLineDataSet.setColor(Color.RED);
                        painLineDataSet.setValueTextSize(10f);
                        weatherLineDataSet.setValueTextSize(10f);

                        LineData lineData=new LineData();
                        lineData.addDataSet(painLineDataSet);
                        lineData.addDataSet(weatherLineDataSet);
                        // xAxis
                        XAxis xAxis = binding.lineChart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setDrawGridLines(false);
                        binding.lineChart.setVisibleXRange(1,1);
                        binding.lineChart.setData(lineData);
                        binding.lineChart.invalidate();

                        testCorrelation(painSelectedList, spinnerItemSelected);
                    }
                });
            }
        });

        binding.buttonGenerateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvRValue.setText(correValue);
                binding.tvPValue.setText(pValue);
            }
        });

        return view;
    }



    public void setFragment(int id){
        Fragment fragment = null;

        switch (id){
            case 0:
                fragment = new ReportFragment();
                break;
            case 1:
                fragment = new StepTakenFragment();
                break;
            case 2:
        }

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container_view, fragment);
            fragmentTransaction.commit();
        }
    }

    public Boolean dateValidation(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date startDateFormat = null;
        Date endDateFormat= null;
        try {
            startDateFormat = sdf.parse(startDate);
            endDateFormat = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (startDateFormat.after(endDateFormat)){
            return false;
        }
        return true;
    }

    public void testCorrelation(List<Pain> painList, String itemSelected){
        double data[][] = new double[painList.size()][];
        for (int i=0; i <painList.size();i++){
            if (itemSelected.equals("Temperature")){
                double[] tempArray = {Double.parseDouble(painList.get(i).getTemperature()), Double.parseDouble(painList.get(i).getLevel())};
                data[i] = tempArray;
            } else if (itemSelected.equals("Humidity")){
                double[] tempArray = {Double.parseDouble(painList.get(i).getHumidity()), Double.parseDouble(painList.get(i).getLevel())};
                data[i] = tempArray;
            } else {
                double[] tempArray = {Double.parseDouble(painList.get(i).getPressure()), Double.parseDouble(painList.get(i).getLevel())};
                data[i] = tempArray;
            }
        }

        // create a realmatrix
        RealMatrix m = MatrixUtils.createRealMatrix(data);
        // measure all correlation test: x-x, x-y, y-x, y-x
        for (int i = 0; i < m.getColumnDimension(); i++)
            for (int j = 0; j < m.getColumnDimension(); j++) {
                PearsonsCorrelation pc = new PearsonsCorrelation();
                double cor = pc.correlation(m.getColumn(i), m.getColumn(j));
                System.out.println(i + "," + j + "=[" + String.format(".%2f", cor) + "," + "]");
            }
        // correlation test (another method): x-y
        PearsonsCorrelation pc = new PearsonsCorrelation(m);
        RealMatrix corM = pc.getCorrelationMatrix();
// significant test of the correlation coefficient (p-value)
        RealMatrix pM = pc.getCorrelationPValues();
        String value = "p value:" + pM.getEntry(0, 1)+ "\n" + " correlation: " +
                corM.getEntry(0, 1);
        pValue = String.valueOf(pM.getEntry(0, 1));
        correValue = String.valueOf(corM.getEntry(0, 1));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}