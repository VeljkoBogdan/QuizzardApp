package com.veljkobogdan.quizzardapp.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.io.Serializable;

@Entity(primaryKeys = {"flashcardId", "flashcardSetId"})
public class FlashcardSetCrossRef implements Serializable {
    @ColumnInfo
    public long flashcardId;
    @ColumnInfo
    public long flashcardSetId;

    public FlashcardSetCrossRef () {}

    @Ignore
    public FlashcardSetCrossRef(long flashcardId, long flashcardSetId) {
        this.flashcardId = flashcardId;
        this.flashcardSetId = flashcardSetId;
    }
}
