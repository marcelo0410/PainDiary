package com.example.paindiary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.paindiary.Adapter.RecyclerViewAdapter;
import com.example.paindiary.Entity.Pain;
import com.example.paindiary.databinding.FragmentDailyRecordBinding;
import com.example.paindiary.viewmodel.PainViewModel;

import java.util.List;


public class DailyRecordFragment extends Fragment {
    private FragmentDailyRecordBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private List<Pain> painList;
    private RecyclerViewAdapter adapter;
    private PainViewModel painViewModel;

    public DailyRecordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDailyRecordBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        painViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PainViewModel.class);
        painViewModel.getAllPainRecord().observe(getViewLifecycleOwner(), new Observer<List<Pain>>() {
            @Override
            public void onChanged(List<Pain> pains) {
                adapter = new RecyclerViewAdapter(pains);
                binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
                binding.recyclerView.setAdapter(adapter);
                layoutManager = new LinearLayoutManager(getContext());
                binding.recyclerView.setLayoutManager(layoutManager);
            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}