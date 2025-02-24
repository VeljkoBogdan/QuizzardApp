package com.veljkobogdan.quizzardapp.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.io.Serializable;

@Entity(primaryKeys = {"questionId", "examId"})
public class ExamQuestionsCrossRef implements Serializable {
    @ColumnInfo
    public long questionId;
    @ColumnInfo
    public long examId;

    public ExamQuestionsCrossRef() {}

    @Ignore
    public ExamQuestionsCrossRef(long questionId, long examId) {
        this.questionId = questionId;
        this.examId = examId;
    }
}
