package com.example.paindiary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.paindiary.databinding.FragmentDataEntryBinding;
import com.example.paindiary.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class DataEntryFragment extends Fragment {

    private FragmentDataEntryBinding binding;
    private ArrayList<MoodItem> moodItemArrayList;
    private MoodAdapter moodAdapter;

    public DataEntryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDataEntryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // spinner
        List<String> painLevelList = new ArrayList<String>();
        painLevelList.add("0 No pain");
        painLevelList.add("1");
        painLevelList.add("2");
        painLevelList.add("3");
        painLevelList.add("4");
        painLevelList.add("5 Moderate pain");
        painLevelList.add("6");
        painLevelList.add("7");
        painLevelList.add("8");
        painLevelList.add("9");
        painLevelList.add("10 Worst possible pain");
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, painLevelList);
        binding.painLevelSpinner.setAdapter(spinnerAdapter);

        List<String> locationList = new ArrayList<String>();
        locationList.add("Back");
        locationList.add("Neck");
        locationList.add("Head");
        locationList.add("Knees");
        locationList.add("Hips");
        locationList.add("Adbomen");
        locationList.add("Elbows");
        locationList.add("Shoulders");
        locationList.add("Shins");
        locationList.add("Jaw");
        locationList.add("facial");
        final ArrayAdapter<String> locationSpinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, locationList);
        binding.painLocationSpinner.setAdapter(locationSpinnerAdapter);

        initList();
        moodAdapter = new MoodAdapter(view.getContext(), moodItemArrayList);
        binding.moodSpinner.setAdapter(moodAdapter);
        binding.moodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MoodItem clickMood = (MoodItem) parent.getItemAtPosition(position);
                String clickMoodName = clickMood.getMoodName();
                Toast.makeText(view.getContext(), clickMoodName + "selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void initList(){
        moodItemArrayList = new ArrayList<>();
        moodItemArrayList.add(new MoodItem("Very good", R.drawable.a));
        moodItemArrayList.add(new MoodItem("Good", R.drawable.b));
        moodItemArrayList.add(new MoodItem("Average", R.drawable.c));
        moodItemArrayList.add(new MoodItem("Low", R.drawable.d));
        moodItemArrayList.add(new MoodItem("Very low", R.drawable.e));
    }

}