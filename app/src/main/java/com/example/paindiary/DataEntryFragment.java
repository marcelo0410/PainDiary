package com.example.paindiary;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.paindiary.Entity.Pain;
import com.example.paindiary.databinding.FragmentDataEntryBinding;
import com.example.paindiary.viewmodel.PainViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class DataEntryFragment extends Fragment {

    private FragmentDataEntryBinding binding;
    private ArrayList<MoodItem> moodItemArrayList;
    private MoodAdapter moodAdapter;
    private PainViewModel painViewModel;
    private String temp;
    private String humidity;
    private String pressure;

    public DataEntryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDataEntryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.timepicker.setIs24HourView(true);

        // receive userEmail
        String userEmail = getArguments().getString("userEmail");



        // painviewmodel
        painViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(PainViewModel.class);
        // preAddedPainData();
        //painViewModel.deleteAll();

        // Task 3 Advanced level: notification
        createNotificationChannel();
        binding.buttonSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = 0;
                int minute = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hour = binding.timepicker.getHour();
                    minute = binding.timepicker.getMinute();
                }
                Calendar calender = Calendar.getInstance();
                calender.set(Calendar.HOUR_OF_DAY, hour);
                calender.set(Calendar.MINUTE, minute);
                calender.set(Calendar.SECOND, 0);
                calender.set(Calendar.MILLISECOND, 0);

                long millis = calender.getTimeInMillis();
                long twoMin = 1000 * 120;
                millis = millis-twoMin;

                Toast.makeText(getContext(), String.valueOf(millis) + " , " +String.valueOf(System.currentTimeMillis()), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), Reminder.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

                long currentTime = System.currentTimeMillis();


                alarmManager.set(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
            }
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
                                                       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                                       String currentDate = sdf.format(new Date());
                                                       // fake weather data
                                                       temp = "20";
                                                       humidity = "30";
                                                       pressure = "1000";


                                                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                                                           CompletableFuture<Pain> painCompletableFuture = painViewModel.findByDateFuture(currentDate);
                                                           painCompletableFuture.thenApply(pain -> {
                                                               if (pain == null) {
                                                                   List<String> painList = dataCollect(v);
                                                                   Pain newPain = new Pain(painList.get(0), painList.get(1), painList.get(2), painList.get(3), painList.get(4), temp, humidity, pressure, userEmail, currentDate);
                                                                   painViewModel.insert(newPain);

                                                               }else {
                                                                   List<String> painList = dataCollect(v);
                                                                   pain.level = painList.get(0);
                                                                   pain.location = painList.get(1);
                                                                   pain.mood = painList.get(2);
                                                                   pain.stepGoal = painList.get(3);
                                                                   pain.stepPhysical = painList.get(4);
                                                                   painViewModel.update(pain);
                                                               }
                                                               return pain;
                                                           });
                                                       }

                                                       //livedata



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

    private List<String> dataCollect(View v){
        MoodItem mood = (MoodItem) binding.moodSpinner.getSelectedItem();
        String moodName = mood.getMoodName();
        String painLocation = binding.painLocationSpinner.getSelectedItem().toString();
        String painLevel = binding.painLevelSpinner.getSelectedItem().toString().substring(0,1);
        String stepGoal = binding.etStepGoal.getText().toString();
        String stepPhysical = binding.etStepPhysical.getText().toString();
        String concat = moodName +" , "+ painLocation +" , "+ painLevel +" , "+ stepGoal +" , "+ stepPhysical;
        List<String> painList = new ArrayList<>();
        painList.add(painLevel);
        painList.add(painLocation);
        painList.add(moodName);
        painList.add(stepGoal);
        painList.add(stepPhysical);
        return painList;


        // Toast.makeText(v.getContext(), concat, Toast.LENGTH_LONG).show();
    }


    //         locationList.add("Back"); 2
    //        locationList.add("Neck"); 3
    //        locationList.add("Head"); 4
    //        locationList.add("Knees"); 3
    //        locationList.add("Hips"); 3
    private void preAddedPainData(){
        Pain pain1 = new Pain("1", "Back", "Good", "5000", "7000", "18", "23", "1015", "fit5046@gmail.com", "26/04/2021");
        Pain pain2 = new Pain("2", "Neck", "Low", "10000", "4200", "16", "55", "1022", "fit5046@gmail.com", "27/04/2021");
        Pain pain3 = new Pain("3", "Head", "Very low", "3000", "1200", "22", "59", "1018", "fit5046@gmail.com", "28/04/2021");
        Pain pain4 = new Pain("9", "Head", "Low", "6000", "5000", "18", "95", "1007", "fit5046@gmail.com", "29/04/2021");
        Pain pain5 = new Pain("7", "Head", "Average", "10000", "3750", "22", "61", "1000", "fit5046@gmail.com", "30/04/2021");
        Pain pain6 = new Pain("5", "Neck", "Low", "12000", "3400", "21", "71", "1004", "fit5046@gmail.com", "01/05/2021");
        Pain pain7 = new Pain("4", "Neck", "Very low", "9000", "1100", "22", "52", "997", "fit5046@gmail.com", "02/05/2021");
        Pain pain8 = new Pain("2", "Knees", "Good", "2000", "500", "20", "55", "1012", "fit5046@gmail.com", "03/05/2021");
        Pain pain9 = new Pain("1", "Hips", "Very good", "3000", "2400", "22", "49", "1016", "fit5046@gmail.com", "04/05/2021");
        Pain pain10 = new Pain("8", "Head", "Very low", "500", "100", "21", "75", "1015", "fit5046@gmail.com", "05/05/2021");
        Pain pain11 = new Pain("4", "Hips", "Average", "4000", "1500", "22", "56", "1015", "fit5046@gmail.com", "06/05/2021");
        Pain pain12 = new Pain("3", "Back", "Average", "10000", "7500", "19", "86", "1012", "fit5046@gmail.com", "07/05/2021");
        Pain pain13 = new Pain("1", "Knees", "Good", "9000", "5400", "22", "76", "1005", "fit5046@gmail.com", "08/05/2021");
        Pain pain14 = new Pain("5", "Hips", "Low", "7000", "2600", "27", "58", "998", "fit5046@gmail.com", "09/05/2021");
        Pain pain15 = new Pain("2", "Knees", "Good", "10000", "9500", "23", "48", "1009", "fit5046@gmail.com", "10/05/2021");

        painViewModel.insert(pain1);
        painViewModel.insert(pain2);
        painViewModel.insert(pain3);
        painViewModel.insert(pain4);
        painViewModel.insert(pain5);
        painViewModel.insert(pain6);
        painViewModel.insert(pain7);
        painViewModel.insert(pain8);
        painViewModel.insert(pain9);
        painViewModel.insert(pain10);
        painViewModel.insert(pain11);
        painViewModel.insert(pain12);
        painViewModel.insert(pain13);
        painViewModel.insert(pain14);
        painViewModel.insert(pain15);
    }

    private void createNotificationChannel(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "NotifyChannel";
            String description = "Data Entry Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notify", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}