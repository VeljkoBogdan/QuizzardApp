package com.veljkobogdan.quizzardapp.data.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Exam implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long examId;

    public Exam() {}

    public long getExamId() {
        return examId;
    }
}
