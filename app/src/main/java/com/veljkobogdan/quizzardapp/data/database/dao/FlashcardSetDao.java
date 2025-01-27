package com.veljkobogdan.quizzardapp.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.veljkobogdan.quizzardapp.data.database.entities.FlashcardSet;

import java.util.List;

@Dao
public interface FlashcardSetDao {
    @Query("SELECT * FROM flashcardset")
    LiveData<List<FlashcardSet>> getFlashcardSets();

    @Insert
    void insertAll(FlashcardSet... flashcardSets);

    @Insert
    void insert(FlashcardSet flashcardSet);

    @Update
    void update(FlashcardSet flashcardSet);

    @Delete
    void delete(FlashcardSet flashcardSet);
}
