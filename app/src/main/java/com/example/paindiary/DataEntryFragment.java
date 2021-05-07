package com.example.paindiary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.paindiary.databinding.FragmentDataEntryBinding;
import com.example.paindiary.viewmodel.DataViewModel;

import java.util.ArrayList;
import java.util.List;

public class DataEntryFragment extends Fragment {

    private FragmentDataEntryBinding binding;
    private ArrayList<MoodItem> moodItemArrayList;
    private MoodAdapter moodAdapter;
    private DataViewModel model;

    public DataEntryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDataEntryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        model = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        model.getData().observe(getViewLifecycleOwner(), list -> {
            String useremail = list.get(0);
            Log.d("useremail Data entry", useremail);
            binding.etShow.setText(useremail);
        });



        // pain level spinner
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

        // pain location spinner
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

        // moodSpinner
        initList();
        moodAdapter = new MoodAdapter(view.getContext(), moodItemArrayList);
        binding.moodSpinner.setAdapter(moodAdapter);
        binding.moodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MoodItem clickMood = (MoodItem) parent.getItemAtPosition(position);
                String clickMoodName = clickMood.getMoodName();
                // Toast.makeText(view.getContext(), clickMoodName + "selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.btnEdit.setEnabled(false);
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   Boolean flag = dataEntryValidation();
                                                   if (flag){
                                                       Toast.makeText(view.getContext(), "Save Successfully!!", Toast.LENGTH_LONG).show();
                                                       binding.etStepGoal.setFocusable(false);
                                                       binding.etStepPhysical.setFocusable(false);

                                                       binding.moodSpinner.setEnabled(false);
                                                       binding.moodSpinner.setClickable(false);
                                                       binding.moodSpinner.setAdapter(moodAdapter);
                                                       binding.painLevelSpinner.setEnabled(false);
                                                       binding.painLevelSpinner.setClickable(false);
                                                       binding.painLevelSpinner.setAdapter(spinnerAdapter);
                                                       binding.painLocationSpinner.setEnabled(false);
                                                       binding.painLocationSpinner.setClickable(false);
                                                       binding.painLocationSpinner.setAdapter(locationSpinnerAdapter);
                                                       binding.btnSave.setEnabled(false);
                                                       binding.btnEdit.setEnabled(true);
                                                       binding.timepicker.setEnabled(false);
                                                       binding.timepicker.setClickable(false);
                                                       // receive data from user

                                                       //livedata


                                                       dataCollect(v);
                                                   }
                                               }
                                           });

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "Please edit your data!", Toast.LENGTH_LONG).show();
                binding.etStepGoal.setFocusableInTouchMode(true);
                binding.etStepPhysical.setFocusableInTouchMode(true);
                binding.moodSpinner.setEnabled(true);
                binding.moodSpinner.setClickable(true);
                binding.moodSpinner.setAdapter(moodAdapter);
                binding.painLevelSpinner.setEnabled(true);
                binding.painLevelSpinner.setClickable(true);
                binding.painLevelSpinner.setAdapter(spinnerAdapter);
                binding.painLocationSpinner.setEnabled(true);
                binding.painLocationSpinner.setClickable(true);
                binding.painLocationSpinner.setAdapter(locationSpinnerAdapter);
                binding.btnSave.setEnabled(true);
                binding.btnEdit.setEnabled(false);
                binding.timepicker.setEnabled(true);
                binding.timepicker.setClickable(true);
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

    private boolean dataEntryValidation(){
        // check empty
        if (binding.etStepGoal.getText().toString().trim().isEmpty()){
            binding.etStepGoal.setError("Please enter your step goal!");
            binding.etStepGoal.requestFocus();
            return false;
        }

        if (binding.etStepPhysical.getText().toString().trim().isEmpty()){
            binding.etStepPhysical.setError("Please enter today's physical steps!");
            binding.etStepPhysical.requestFocus();
            return false;
        }

        try {
            Integer.parseInt(binding.etStepPhysical.getText().toString().trim());
        } catch (Exception e){
            binding.etStepPhysical.setError("Please enter a number!");
            binding.etStepPhysical.requestFocus();
            return false;
        }

        try {
            Integer.parseInt(binding.etStepGoal.getText().toString().trim());
        } catch (Exception e){
            binding.etStepGoal.setError("Please enter a number!");
            binding.etStepGoal.requestFocus();
            return false;
        }
        return true;
    }

    private void dataCollect(View v){
        MoodItem mood = (MoodItem) binding.moodSpinner.getSelectedItem();
        String moodName = mood.getMoodName();
        String painLocation = binding.painLocationSpinner.getSelectedItem().toString();
        String painLevel = binding.painLevelSpinner.getSelectedItem().toString().substring(0,1);
        String stepGoal = binding.etStepGoal.getText().toString();
        String stepPhysical = binding.etStepPhysical.getText().toString();
        String concat = moodName +" , "+ painLocation +" , "+ painLevel +" , "+ stepGoal +" , "+ stepPhysical;
        Toast.makeText(v.getContext(), concat, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}