package com.veljkobogdan.quizzardapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "calendar_insert")
public class CalendarInsert implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "date")
    public String date;
    @ColumnInfo(name = "time")
    public String time;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public CalendarInsert setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CalendarInsert setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDate() {
        return date;
    }

    public CalendarInsert setDate(String date) {
        this.date = date;
        return this;
    }

    public String getTime() {
        return time;
    }

    public CalendarInsert setTime(String time) {
        this.time = time;
        return this;
    }
}
