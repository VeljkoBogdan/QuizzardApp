package com.veljkobogdan.quizzardapp.data.database.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Question implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long questionId;

    public String question;
    public String answer;

    public Question() {}

    @Ignore
    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public long getQuestionId() {
        return questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
