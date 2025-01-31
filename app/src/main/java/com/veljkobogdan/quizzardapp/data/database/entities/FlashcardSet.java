package com.veljkobogdan.quizzardapp.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class FlashcardSet implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long flashcardSetId;

    @ColumnInfo
    public String name;

    public FlashcardSet() {}

    public FlashcardSet(String title) {
        this.name = title;
    }

    public long getFlashcardSetId() {
        return flashcardSetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
