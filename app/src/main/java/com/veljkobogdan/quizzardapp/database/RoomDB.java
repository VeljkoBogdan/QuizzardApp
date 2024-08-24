package com.veljkobogdan.quizzardapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.veljkobogdan.quizzardapp.entity.Note;

@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB db;
    private static String DB_NAME = "QuizzardApp";

    public synchronized static RoomDB getInstance(Context context){
        if (db == null){
            db = Room.databaseBuilder(
                    context.getApplicationContext(),
                    RoomDB.class,
                    DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return db;
    }

    public abstract NoteDAO noteDAO();
}
