package com.veljkobogdan.quizzardapp.data.models;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Junction;
import androidx.room.Relation;

import com.veljkobogdan.quizzardapp.data.database.entities.Flashcard;
import com.veljkobogdan.quizzardapp.data.database.entities.FlashcardSet;
import com.veljkobogdan.quizzardapp.data.database.entities.FlashcardSetCrossRef;

import java.io.Serializable;
import java.util.List;

public class FlashcardSetWithFlashcards implements Serializable {
    @Embedded
    public FlashcardSet flashcardSet;

    @Relation(
            parentColumn = "flashcardSetId",
            entityColumn = "flashcardId",
            associateBy = @Junction(FlashcardSetCrossRef.class)
    )
    public List<Flashcard> flashcards;

    public FlashcardSetWithFlashcards() {}

    @Ignore
    public FlashcardSetWithFlashcards(FlashcardSet flashcardSet, List<Flashcard> flashcards) {
        this.flashcardSet = flashcardSet;
        this.flashcards = flashcards;
    }
}
