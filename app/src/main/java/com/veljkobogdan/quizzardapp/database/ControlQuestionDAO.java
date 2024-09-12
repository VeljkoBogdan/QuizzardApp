package com.veljkobogdan.quizzardapp.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.veljkobogdan.quizzardapp.entity.ControlQuestion;

import java.util.List;

@Dao
public interface ControlQuestionDAO {
    @Insert(onConflict = REPLACE)
    void insert(ControlQuestion controlQuestion);

    @Query("SELECT * FROM control_questions")
    List<ControlQuestion> getAll();

    @Query("UPDATE control_questions SET answer = :answer, question = :question WHERE id = :id")
    void update(int id, String answer, String question);

    @Delete
    void delete(ControlQuestion controlQuestion);
}
