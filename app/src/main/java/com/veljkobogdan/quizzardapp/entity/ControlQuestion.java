package com.veljkobogdan.quizzardapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "control_questions")
public class ControlQuestion implements Serializable {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "question")
    public String question;
    @ColumnInfo(name = "answer")
    public String answer;

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public ControlQuestion setQuestion(String question) {
        this.question = question;
        return this;
    }

    public String getAnswer() {
        return answer;
    }

    public ControlQuestion setAnswer(String answer) {
        this.answer = answer;
        return this;
    }
}
