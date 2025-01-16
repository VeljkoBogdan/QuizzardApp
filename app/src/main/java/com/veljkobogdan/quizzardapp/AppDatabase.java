package com.veljkobogdan.quizzardapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.veljkobogdan.quizzardapp.dao.NoteDao;
import com.veljkobogdan.quizzardapp.entities.Note;

@Database(entities = {
        Note.class
}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public static String name = "quizzard-db";

    public abstract NoteDao noteDao();
}
