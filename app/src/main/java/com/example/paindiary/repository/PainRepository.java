package com.example.paindiary.repository;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.paindiary.Entity.Pain;
import com.example.paindiary.dao.PainDAO;
import com.example.paindiary.database.PainDatabase;

import java.security.cert.CertificateParsingException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class PainRepository {
    private PainDAO painDao;
    private LiveData<List<Pain>> allPainRecord;
    public PainRepository(Application application){
        PainDatabase db = PainDatabase.getInstance(application);
        painDao =db.painDao();
        allPainRecord = painDao.getAll();
    }
    // Room executes this query on a separate thread
    public LiveData<List<Pain>> getAllPainRecord() {
        return allPainRecord;
    }
    public void insert(final Pain pain){
        PainDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                painDao.insert(pain);
            }
        });
    }
    public void deleteAll(){
        PainDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                painDao.deleteAll();
            }
        });
    }
    public void delete(final Pain pain){
        PainDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                painDao.delete(pain);
            }
        });
    }
    public void updatePainRecord(final Pain pain){
        PainDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                painDao.updatePain(pain);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Pain> findByIDFuture(final int painId) {
        return CompletableFuture.supplyAsync(new Supplier<Pain>() {
            @Override
            public Pain get() {
                return painDao.findByID(painId);
            }
        }, PainDatabase.databaseWriteExecutor);
    }
}