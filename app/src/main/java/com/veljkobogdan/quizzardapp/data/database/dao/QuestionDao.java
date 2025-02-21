package com.veljkobogdan.quizzardapp.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.veljkobogdan.quizzardapp.data.database.entities.Question;

import java.util.List;

@Dao
public interface QuestionDao {
    @Query("SELECT * FROM question")
    LiveData<List<Question>> getQuestions();

    @Insert
    void insertAll(Question... questions);

    @Insert
    long insert(Question question);

    @Update
    void update(Question question);

    @Delete
    void delete(Question question);

    @Query("DELETE FROM question")
    void deleteAll();
}
