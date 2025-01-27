package com.veljkobogdan.quizzardapp.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FlashcardSet {
    @PrimaryKey(autoGenerate = true)
    public long flashcardSetId;

    @ColumnInfo
    public String name;

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
