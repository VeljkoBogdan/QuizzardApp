package com.veljkobogdan.quizzardapp.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Exam implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long examId;
    @ColumnInfo
    public String examTitle;

    public Exam() {}

    @Ignore
    public Exam(String examTitle) {
        this.examTitle = examTitle;
    }

    public long getExamId() {
        return examId;
    }

    public String getTitle() {
        return examTitle;
    }
}
