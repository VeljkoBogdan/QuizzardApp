package com.veljkobogdan.quizzardapp.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.veljkobogdan.quizzardapp.entity.Flashcard;
import com.veljkobogdan.quizzardapp.entity.FlashcardCollection;

import java.util.List;

@Dao
public interface FlashcardCollectionDAO {
    @Insert(onConflict = REPLACE)
    void insert(FlashcardCollection flashcardCollection);

    @Query("SELECT * FROM flashcard_collection")
    List<FlashcardCollection> getAll();

    @Query("UPDATE flashcard_collection SET name = :name, flashcards = :flashcards WHERE id = :id")
    void update(int id, String name, List<Flashcard> flashcards);

    @Delete
    void delete(FlashcardCollection flashcardCollection);
}
