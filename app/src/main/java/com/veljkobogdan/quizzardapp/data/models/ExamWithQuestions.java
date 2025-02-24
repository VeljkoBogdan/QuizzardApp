package com.veljkobogdan.quizzardapp.data.models;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Junction;
import androidx.room.Relation;

import com.veljkobogdan.quizzardapp.data.database.entities.Exam;
import com.veljkobogdan.quizzardapp.data.database.entities.Question;

import java.io.Serializable;
import java.util.List;

public class ExamWithQuestions implements Serializable {
    @Embedded
    public Exam exam;

    @Relation(
            parentColumn = "examId",
            entityColumn = "questionId",
            associateBy = @Junction(ExamWithQuestions.class)
    )
    public List<Question> questionList;

    public ExamWithQuestions() {}

    @Ignore
    public ExamWithQuestions(Exam exam, List<Question> questionList) {
        this.exam = exam;
        this.questionList = questionList;
    }

}
