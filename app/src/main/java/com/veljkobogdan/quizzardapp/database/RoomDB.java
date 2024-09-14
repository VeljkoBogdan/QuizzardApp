package com.veljkobogdan.quizzardapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.veljkobogdan.quizzardapp.entity.CalendarInsert;
import com.veljkobogdan.quizzardapp.entity.ControlQuestion;
import com.veljkobogdan.quizzardapp.entity.Flashcard;
import com.veljkobogdan.quizzardapp.entity.FlashcardCollection;
import com.veljkobogdan.quizzardapp.entity.Note;
import com.veljkobogdan.quizzardapp.database.typeconverters.FlashcardListConverter;

@Database(entities = {
        Note.class,
        CalendarInsert.class,
        Flashcard.class,
        FlashcardCollection.class,
        ControlQuestion.class},
        version = 6, exportSchema = false)
@TypeConverters({FlashcardListConverter.class})
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
    public abstract FlashcardDAO flashcardDAO();
    public abstract FlashcardCollectionDAO flashcardCollectionDAO();
    public abstract ControlQuestionDAO controlQuestionDAO();
}
