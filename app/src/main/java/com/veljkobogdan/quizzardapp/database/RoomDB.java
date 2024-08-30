package com.veljkobogdan.quizzardapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.veljkobogdan.quizzardapp.entity.CalendarInsert;
import com.veljkobogdan.quizzardapp.entity.Note;

@Database(entities = {Note.class, CalendarInsert.class}, version = 2, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB db;

    public synchronized static RoomDB getInstance(Context context){
        if (db == null){
            String DB_NAME = "QuizzardApp";
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
    public abstract CalendarDAO calendarDAO();
}
