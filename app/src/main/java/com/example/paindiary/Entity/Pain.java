package com.example.paindiary.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Pain {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "pain_level")
    @NonNull
    public String level;

    @ColumnInfo(name = "pain_Location")
    @NonNull
    public String location;

    @ColumnInfo(name = "pain_mood")
    @NonNull
    public String mood;

    @ColumnInfo(name = "pain_stepGoal")
    @NonNull
    public String stepGoal;

    @ColumnInfo(name = "pain_stepPhy")
    @NonNull
    public String stepPhysical;

    @ColumnInfo(name = "pain_temperature")
    @NonNull
    public String temperature;

    @ColumnInfo(name = "pain_humidity")
    @NonNull
    public String humidity;

    @ColumnInfo(name = "pain_pressure")
    @NonNull
    public String pressure;

    @ColumnInfo(name = "pain_userEmail")
    @NonNull
    public String userEmail;

    @ColumnInfo(name = "pain_date")
    @NonNull
    public String date;


    public Pain(@NonNull String level, @NonNull String location, @NonNull String mood, @NonNull String stepGoal, @NonNull String stepPhysical, @NonNull String temperature, @NonNull String humidity, @NonNull String pressure, @NonNull String userEmail, @NonNull String date) {
        this.level = level;
        this.location = location;
        this.mood = mood;
        this.stepGoal = stepGoal;
        this.stepPhysical = stepPhysical;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.userEmail = userEmail;
        this.date = date;
    }
}