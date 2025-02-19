package com.veljkobogdan.quizzardapp.service;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

public class StreakService {
    public static final String STREAK_KEY = "streakKey";
    public static final String DATE_KEY = "dateKey";
    public static final String COUNTER_KEY = "counterKey";

    private SharedPreferences sharedPreferences;
    private Calendar calendar;

    private int thisDay, lastDay, counterOfConsecutiveDays;

    public StreakService(Context context) {
        this.calendar = Calendar.getInstance();
        this.sharedPreferences = context.getSharedPreferences(STREAK_KEY, Context.MODE_PRIVATE);

        this.thisDay = calendar.get(Calendar.DAY_OF_YEAR);
        this.lastDay = sharedPreferences.getInt(DATE_KEY, 0);
        this.counterOfConsecutiveDays = sharedPreferences.getInt(COUNTER_KEY, 0);

        updateStreak();
    }

    private void updateStreak() {
        if (lastDay == thisDay - 1) {
            counterOfConsecutiveDays += 1;
            sharedPreferences
                    .edit()
                    .putInt(DATE_KEY, thisDay)
                    .putInt(COUNTER_KEY, counterOfConsecutiveDays)
                    .apply();
        } else {
            sharedPreferences
                    .edit()
                    .putInt(DATE_KEY, thisDay)
                    .putInt(COUNTER_KEY, 1)
                    .apply();
        }
    }

    public int getCounterOfConsecutiveDays() {
        return counterOfConsecutiveDays;
    }
}
