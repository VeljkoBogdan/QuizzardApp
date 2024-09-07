package com.veljkobogdan.quizzardapp.helper;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.veljkobogdan.quizzardapp.entity.Flashcard;

import java.util.ArrayList;

public class FlashcardListConverter {
    @TypeConverter
    public static String listToJson(ArrayList<Flashcard> flashcards) {
        Gson gson = new Gson();
        return gson.toJson(flashcards);
    }

    @TypeConverter
    public static ArrayList<Flashcard> jsonToList(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<ArrayList<Flashcard>>(){}.getType());
    }
}