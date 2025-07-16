package com.veljkobogdan.quizzardapp.data.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "schedule")
public class Schedule implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long scheduleId;

    public String name;
}

