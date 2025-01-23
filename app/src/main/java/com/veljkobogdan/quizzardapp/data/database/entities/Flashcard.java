package com.veljkobogdan.quizzardapp.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "flashcard")
public class Flashcard {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "term")
    public String term;
    @ColumnInfo(name = "term")
    public String definition;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
