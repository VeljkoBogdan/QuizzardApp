package com.veljkobogdan.quizzardapp.data.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity(tableName = "subjects")
public class Subject implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long subjectId;
    @NonNull
    public String name;
    public String teacherName;
    public String location;
    @NonNull
    public DayOfWeek dayOfWeek;
    @NonNull
    public LocalTime startTime;
    @NonNull
    public LocalTime endTime;
}

