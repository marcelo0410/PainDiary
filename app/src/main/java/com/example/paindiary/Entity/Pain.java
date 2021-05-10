package com.example.paindiary.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Pain {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "pain_date")
    @NonNull
    public String date;

    @ColumnInfo(name = "pain_userEmail")
    @NonNull
    public String userEmail;

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

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    @NonNull
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(@NonNull String userEmail) {
        this.userEmail = userEmail;
    }

    @NonNull
    public String getLevel() {
        return level;
    }

    public void setLevel(@NonNull String level) {
        this.level = level;
    }

    @NonNull
    public String getLocation() {
        return location;
    }

    public void setLocation(@NonNull String location) {
        this.location = location;
    }

    @NonNull
    public String getMood() {
        return mood;
    }

    public void setMood(@NonNull String mood) {
        this.mood = mood;
    }

    @NonNull
    public String getStepGoal() {
        return stepGoal;
    }

    public void setStepGoal(@NonNull String stepGoal) {
        this.stepGoal = stepGoal;
    }

    @NonNull
    public String getStepPhysical() {
        return stepPhysical;
    }

    public void setStepPhysical(@NonNull String stepPhysical) {
        this.stepPhysical = stepPhysical;
    }

    @NonNull
    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(@NonNull String temperature) {
        this.temperature = temperature;
    }

    @NonNull
    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(@NonNull String humidity) {
        this.humidity = humidity;
    }

    @NonNull
    public String getPressure() {
        return pressure;
    }

    public void setPressure(@NonNull String pressure) {
        this.pressure = pressure;
    }
}