package com.veljkobogdan.quizzardapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "flashcard")
public class Flashcard implements Serializable {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "front")
    public String front;
    @ColumnInfo(name = "back")
    public String back;

    public int getId() {
        return id;
    }

    public String getFront() {
        return front;
    }

    public Flashcard setFront(String front) {
        this.front = front;
        return this;
    }

    public String getBack() {
        return back;
    }

    public Flashcard setBack(String back) {
        this.back = back;
        return this;
    }
}
