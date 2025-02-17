package com.veljkobogdan.quizzardapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeManager {
    private static final String PREF_NAME = "settings";
    private static final String THEME_KEY = "theme";

    public static void setTheme(Context context, int themeMode) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(THEME_KEY, themeMode);
        editor.apply();
        AppCompatDelegate.setDefaultNightMode(themeMode);
    }

    public static void applyTheme(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int theme = preferences.getInt(THEME_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        AppCompatDelegate.setDefaultNightMode(theme);
    }

    public static int getCurrentTheme(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(THEME_KEY, 0);
    }
}
