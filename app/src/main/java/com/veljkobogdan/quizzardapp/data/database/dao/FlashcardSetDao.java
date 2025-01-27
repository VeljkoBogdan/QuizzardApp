package com.veljkobogdan.quizzardapp.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.veljkobogdan.quizzardapp.data.database.entities.FlashcardSet;
import com.veljkobogdan.quizzardapp.data.database.entities.FlashcardSetCrossRef;
import com.veljkobogdan.quizzardapp.data.models.FlashcardSetWithFlashcards;

import java.util.List;

@Dao
public interface FlashcardSetDao {
    @Query("SELECT * FROM flashcardset")
    LiveData<List<FlashcardSet>> getFlashcardSets();

    @Insert
    void insertAll(FlashcardSet... flashcardSets);

    @Insert
    long insert(FlashcardSet flashcardSet);

    @Update
    void update(FlashcardSet flashcardSet);

    @Delete
    void delete(FlashcardSet flashcardSet);

    @Transaction
    @Query("SELECT * FROM flashcardset")
    LiveData<List<FlashcardSetWithFlashcards>> getFlashcardSetWithFlashcards();

    @Insert
    void insertFlashcardSetCrossRef(FlashcardSetCrossRef flashcardSetCrossRef);
}
