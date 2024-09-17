package com.veljkobogdan.quizzardapp.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.veljkobogdan.quizzardapp.entity.ControlQuestion;
import com.veljkobogdan.quizzardapp.entity.Exam;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ExamDAO {
    @Insert(onConflict = REPLACE)
    void insert(Exam exam);

    @Query("SELECT * FROM exam")
    List<Exam> getAll();

    @Query("UPDATE exam SET name = :name, control_questions = :controlQuestions WHERE id = :id")
    void update(int id, String name, ArrayList<ControlQuestion> controlQuestions);

    @Delete
    void delete(Exam exam);
}
