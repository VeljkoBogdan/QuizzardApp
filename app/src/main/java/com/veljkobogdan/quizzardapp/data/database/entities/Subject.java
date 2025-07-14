package com.veljkobogdan.quizzardapp.data.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subjects")
public class Subject {
    @PrimaryKey(autoGenerate = true)
    public long subjectId;

    @NonNull
    public String name;

    public String teacherName;
    public String location;
    public String colorHex;
}

