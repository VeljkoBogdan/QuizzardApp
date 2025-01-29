package com.veljkobogdan.quizzardapp.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tag")
public class Tag implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long tagId;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "color")
    public String color;

    @Ignore
    public Tag(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Tag() {}

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
