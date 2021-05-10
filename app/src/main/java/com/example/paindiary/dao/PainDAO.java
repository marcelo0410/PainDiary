package com.example.paindiary.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.paindiary.Entity.Pain;

import java.util.List;

@Dao
public interface PainDAO {
    @Query("SELECT * FROM pain ORDER BY uid ASC")
    LiveData<List<Pain>> getAll();

    @Query("SELECT * FROM pain WHERE uid = :painId LIMIT 1")
    Pain findByID(int painId);

    @Query("SELECT * FROM pain WHERE pain_date = :painDate LIMIT 1")
    Pain findByDate(String painDate);

    @Insert
    void insert(Pain pain);

    @Delete
    void delete(Pain pain);

    @Update
    void updatePain(Pain pain);

    @Query("DELETE FROM pain")
    void deleteAll();
}
