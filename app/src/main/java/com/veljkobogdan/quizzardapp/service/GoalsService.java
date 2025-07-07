package com.veljkobogdan.quizzardapp.service;

import android.content.Context;
import android.content.SharedPreferences;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;

public class GoalsService {
    public enum DailyGoalType {
        REVIEW_FLASHCARDS,
        PRACTICE_EXAM,
        WRITE_NOTE
    }

    private static final String PREF_NAME = "daily_goals";
    private static final String PREF_DATE = "goal_date";

    private final SharedPreferences preferences;

    public GoalsService(Context context) {
        this.preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        resetIfNewDay();
    }

    public void resetIfNewDay() {
        String today = LocalDate.now().toString();
        String storedDate = preferences.getString(PREF_DATE, null);
        if (!today.equals(storedDate)) {
            resetAllGoals();
        }
    }

    public void markGoalCompleted(DailyGoalType type) {
        preferences.edit().putBoolean(type.name(), true).apply();
    }

    public boolean isGoalCompleted(DailyGoalType type) {
        return preferences.getBoolean(type.name(), false);
    }

    public Map<DailyGoalType, Boolean> getAllGoalStatuses() {
        Map<DailyGoalType, Boolean> statuses = new EnumMap<>(DailyGoalType.class);
        for (DailyGoalType goal : DailyGoalType.values()) {
            statuses.put(goal, isGoalCompleted(goal));
        }
        return statuses;
    }

    public void resetAllGoals() {
        preferences.edit().clear().apply();
        preferences.edit().putString(PREF_DATE, LocalDate.now().toString()).apply();
    }

    public int getCompletedGoalCount() {
        int count = 0;
        for (DailyGoalType goal : DailyGoalType.values()) {
            if (isGoalCompleted(goal)) {
                count++;
            }
        }
        return count;
    }
}
