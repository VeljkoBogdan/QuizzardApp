package com.veljkobogdan.quizzardapp.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.veljkobogdan.quizzardapp.data.database.entities.Exam;
import com.veljkobogdan.quizzardapp.data.database.entities.ExamQuestionsCrossRef;
import com.veljkobogdan.quizzardapp.data.models.ExamWithQuestions;

import java.util.List;

@Dao
public interface ExamDao {
    @Query("SELECT * FROM exam")
    LiveData<List<Exam>> getExams();

    @Insert
    void insertAll(Exam... exams);

    @Insert
    long insert(Exam exam);

    @Update
    void update(Exam exam);

    @Delete
    void delete(Exam exam);

    @Transaction
    @Query("SELECT * FROM exam")
    LiveData<List<ExamWithQuestions>> getExamsWithQuestions();

    @Insert
    void insertExamQuestionCrossRef(ExamQuestionsCrossRef examQuestionsCrossRef);

    @Query("DELETE FROM examquestionscrossref WHERE examId = :examId")
    void deleteExamQuestionCrossRef(long examId);

    @Query("DELETE FROM question WHERE questionId IN (SELECT questionId FROM examquestionscrossref WHERE examId = :examId)")
    void deleteQuestionsInExam(long examId);

    @Query("DELETE FROM exam")
    void deleteAll();

    @Query("DELETE FROM examquestionscrossref")
    void deleteAllReferences();
}
