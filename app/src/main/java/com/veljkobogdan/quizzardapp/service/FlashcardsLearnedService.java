package com.veljkobogdan.quizzardapp.service;

import android.content.Context;
import android.content.SharedPreferences;

public class FlashcardsLearnedService {
    private static final String PREF_NAME = "flashcards_learned_name";
    private static final String PREF_COUNT = "flashcards_learned_count";
    private final SharedPreferences preferences;

    public FlashcardsLearnedService(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void addCount(int amount) {
        int current = getCount();
        preferences.edit().putInt(PREF_COUNT, current + amount).apply();
    }

    public int getCount() {
        return preferences.getInt(PREF_COUNT, 0);
    }

    public void reset() {
        preferences.edit().putInt(PREF_COUNT, 0).apply();
    }
}
