package com.veljkobogdan.quizzardapp.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "flashcard")
public class Flashcard {
    @PrimaryKey(autoGenerate = true)
    public long flashcardId;

    @ColumnInfo(name = "term")
    public String term;
    @ColumnInfo(name = "definition")
    public String definition;

    public long getFlashcardId() {
        return flashcardId;
    }

    public void setFlashcardId(long flashcardId) {
        this.flashcardId = flashcardId;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
