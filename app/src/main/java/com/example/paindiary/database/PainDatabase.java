package com.example.paindiary.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.paindiary.Entity.Pain;
import com.example.paindiary.dao.PainDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Pain.class}, version = 1, exportSchema = false)
public abstract class PainDatabase extends RoomDatabase {
    public abstract PainDAO painDao();
    private static PainDatabase INSTANCE;
    //we create an ExecutorService with a fixed thread pool so we can later run
    // database operations asynchronously on a background thread.
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    //A synchronized method in a multi threaded environment means that two threads
    // are not allowed to access data at the same time
    public static synchronized PainDatabase getInstance(final Context
                                                                    context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    PainDatabase.class, "PainDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
