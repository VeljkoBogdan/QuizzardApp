package com.veljkobogdan.quizzardapp.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.veljkobogdan.quizzardapp.entity.Flashcard;

import java.util.List;

@Dao
public interface FlashcardDAO {
    @Insert(onConflict = REPLACE)
    void insert(Flashcard flashcard);

    @Query("SELECT * FROM flashcard")
    List<Flashcard> getAll();

    @Query("UPDATE flashcard SET back = :back, front = :front WHERE id = :id")
    void update(int id, String back, String front);

    @Delete
    void delete(Flashcard flashcard);
}
