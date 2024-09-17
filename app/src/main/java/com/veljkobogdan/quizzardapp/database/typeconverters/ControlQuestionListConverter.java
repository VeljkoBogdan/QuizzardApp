package com.veljkobogdan.quizzardapp.database.typeconverters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.veljkobogdan.quizzardapp.entity.ControlQuestion;

import java.util.ArrayList;

public class ControlQuestionListConverter {
    @TypeConverter
    public static String listToJson(ArrayList<ControlQuestion> controlQuestions) {
        Gson gson = new Gson();
        return gson.toJson(controlQuestions);
    }

    @TypeConverter
    public static ArrayList<ControlQuestion> jsonToList(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<ArrayList<ControlQuestion>>(){}.getType());
    }
}
