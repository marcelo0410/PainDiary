package com.example.paindiary.Adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paindiary.Entity.Pain;
import com.example.paindiary.databinding.PainRecordLayoutBinding;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Pain> painResults;

    public RecyclerViewAdapter(List<Pain> painResults) {
        this.painResults = painResults;
    }

    //This method creates a new view holder that is constructed with a new View,
    // inflated from a layout
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PainRecordLayoutBinding binding= PainRecordLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }


    // this method binds the view holder created with data that will be displayed
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {
        final Pain pain = painResults.get(position);
        viewHolder.binding.tvDate.setText(pain.getDate());
        viewHolder.binding.tvEmail.setText(pain.getUserEmail());
        viewHolder.binding.tvPainLevel.setText(pain.getLevel());
        viewHolder.binding.tvPainLocation.setText(pain.getLocation());
        viewHolder.binding.tvPainMood.setText(pain.getMood());
        viewHolder.binding.tvPainStepGoal.setText(pain.getStepGoal());
        viewHolder.binding.tvPainStepPhy.setText(pain.getStepPhysical());
        viewHolder.binding.tvTemp.setText(pain.getTemperature());
        viewHolder.binding.tvHumidity.setText(pain.getHumidity());
        viewHolder.binding.tvPressure.setText(pain.getPressure());
    }
    @Override
    public int getItemCount() {
        return painResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private PainRecordLayoutBinding binding;
        public ViewHolder(PainRecordLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
