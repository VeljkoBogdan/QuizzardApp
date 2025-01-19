package com.veljkobogdan.quizzardapp.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.veljkobogdan.quizzardapp.data.database.dao.NoteDao;
import com.veljkobogdan.quizzardapp.data.database.dao.TagDao;
import com.veljkobogdan.quizzardapp.data.database.entities.Note;
import com.veljkobogdan.quizzardapp.data.database.entities.NoteTagCrossRef;
import com.veljkobogdan.quizzardapp.data.database.entities.Tag;

@Database(entities = {
        Note.class,
        Tag.class,
        NoteTagCrossRef.class
}, version = 8)
public abstract class AppDatabase extends RoomDatabase {
    public static AppDatabase db;
    public static String name = "quizzard";

    public synchronized static AppDatabase getInstance(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, name)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return db;
    }

    public abstract NoteDao noteDao();
    public abstract TagDao tagDao();
}
