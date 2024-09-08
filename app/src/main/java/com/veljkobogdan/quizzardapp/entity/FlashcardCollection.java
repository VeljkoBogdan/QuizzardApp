package com.veljkobogdan.quizzardapp.entity;

import androidx.room.ColumnInfo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "flashcard_collection")
public class FlashcardCollection implements Serializable {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "flashcards")
    public ArrayList<Flashcard> flashcards;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public FlashcardCollection setName(String name) {
        this.name = name;
        return this;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public FlashcardCollection setFlashcards(ArrayList<Flashcard> flashcards) {
        this.flashcards = flashcards;
        return this;
    }
}