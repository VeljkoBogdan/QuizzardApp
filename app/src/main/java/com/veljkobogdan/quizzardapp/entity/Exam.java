package com.veljkobogdan.quizzardapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "exam")
public class Exam implements Serializable {

    @PrimaryKey
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "control_questions")
    public ArrayList<ControlQuestion> controlQuestions;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Exam setName(String name) {
        this.name = name;
        return this;
    }

    public ArrayList<ControlQuestion> getControlQuestions() {
        return controlQuestions;
    }

    public Exam setControlQuestions(ArrayList<ControlQuestion> controlQuestions) {
        this.controlQuestions = controlQuestions;
        return this;
    }
}
