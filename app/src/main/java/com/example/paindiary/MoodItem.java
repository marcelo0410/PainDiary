package com.example.paindiary;

public class MoodItem {
    private String moodName;
    private int moodImage;

    public MoodItem(String moodName, int moodImage) {
        this.moodName = moodName;
        this.moodImage = moodImage;
    }

    public String getMoodName() {
        return moodName;
    }

    public void setMoodName(String moodName) {
        this.moodName = moodName;
    }

    public int getMoodImage() {
        return moodImage;
    }

    public void setMoodImage(int moodImage) {
        this.moodImage = moodImage;
    }
}
