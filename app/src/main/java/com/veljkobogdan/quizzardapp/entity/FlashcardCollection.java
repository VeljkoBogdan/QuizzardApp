package com.veljkobogdan.quizzardapp.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "flashcard_collection")
public class FlashcardCollection implements Serializable {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "flashcards")
    public ArrayList<Flashcard> flashcards;
    @ColumnInfo(name = "description")
    public String description;

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

    public ArrayList<Flashcard> getFlashcards() {
        return flashcards;
    }

    public FlashcardCollection setFlashcards(ArrayList<Flashcard> flashcards) {
        this.flashcards = flashcards;
        return this;
    }

    public FlashcardCollection setDescription(String description) {
        this.description = description;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
