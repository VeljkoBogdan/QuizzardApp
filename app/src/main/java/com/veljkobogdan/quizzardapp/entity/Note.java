package com.veljkobogdan.quizzardapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notes")
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String title = "";

    @ColumnInfo(name = "text")
    public String text = "";

    @ColumnInfo(name = "date")
    public String date = "";

    @ColumnInfo(name = "pin")
    public boolean isPinned = false;

    public boolean isPinned() {
        return isPinned;
    }

    public Note setPinned(boolean pinned) {
        isPinned = pinned;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Note setDate(String date) {
        this.date = date;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Note setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getText() {
        return text;
    }

    public Note setText(String text) {
        this.text = text;
        return this;
    }
}
