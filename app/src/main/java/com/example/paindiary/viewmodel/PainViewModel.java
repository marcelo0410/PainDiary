package com.example.paindiary.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.paindiary.Entity.Pain;
import com.example.paindiary.repository.PainRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PainViewModel extends AndroidViewModel {
    private PainRepository painRepository;
    private LiveData<List<Pain>> allPainRecord;

    public PainViewModel(Application application) {
        super(application);
        painRepository = new PainRepository(application);
        allPainRecord = painRepository.getAllPainRecord();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Pain> findByIDFuture(final int painId){
        return painRepository.findByIDFuture(painId);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Pain> findByDateFuture(final String painDate){
        return painRepository.findByDateFuture(painDate);
    }

    public LiveData<List<Pain>> getAllPainRecord() {
        return allPainRecord;
    }

    public void insert(Pain pain) {
        painRepository.insert(pain);
    }

    public void deleteAll() {
        painRepository.deleteAll();
    }

    public void update(Pain pain) {
        painRepository.updatePainRecord(pain);
    }
}
