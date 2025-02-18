package com.veljkobogdan.quizzardapp.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.veljkobogdan.quizzardapp.data.database.entities.Flashcard;

import java.util.List;

@Dao
public interface FlashcardDao {
    @Query("SELECT * FROM flashcard")
    LiveData<List<Flashcard>> getFlashcards();

    @Insert
    void insertAll(Flashcard... flashcards);

    @Insert
    long insert(Flashcard flashcard);

    @Update
    void update(Flashcard flashcard);

    @Delete
    void delete(Flashcard flashcard);

    @Query("DELETE FROM flashcard")
    void deleteAll();
}
