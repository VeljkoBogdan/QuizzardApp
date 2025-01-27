package com.veljkobogdan.quizzardapp.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"flashcardId", "flashcardSetId"})
public class FlashcardSetCrossRef {
    @ColumnInfo
    public long flashcardId;
    @ColumnInfo
    public long flashcardSetId;

    public FlashcardSetCrossRef () {}

    public FlashcardSetCrossRef(long flashcardId, long flashcardSetId) {
        this.flashcardId = flashcardId;
        this.flashcardSetId = flashcardSetId;
    }
}
