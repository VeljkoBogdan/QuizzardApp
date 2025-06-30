package com.veljkobogdan.quizzardapp.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.veljkobogdan.quizzardapp.data.database.AppDatabase;
import com.veljkobogdan.quizzardapp.data.database.dao.FlashcardDao;
import com.veljkobogdan.quizzardapp.data.database.dao.FlashcardSetDao;
import com.veljkobogdan.quizzardapp.data.database.entities.Flashcard;
import com.veljkobogdan.quizzardapp.data.database.entities.FlashcardSet;
import com.veljkobogdan.quizzardapp.data.database.entities.FlashcardSetCrossRef;
import com.veljkobogdan.quizzardapp.data.models.FlashcardSetWithFlashcards;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FlashcardSetRepository {
    private final FlashcardSetDao flashcardSetDao;
    private final FlashcardDao flashcardDao;
    private final ExecutorService executor;

    public FlashcardSetRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.flashcardDao = db.flashcardDao();
        this.flashcardSetDao = db.flashcardSetDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<FlashcardSet>> getFlashcardSets() {
        return flashcardSetDao.getFlashcardSets();
    }

    public LiveData<List<FlashcardSetWithFlashcards>> getFlashcardSetsWithFlashcards() {
        return flashcardSetDao.getFlashcardSetWithFlashcards();
    }

    public void insertFlashcard(FlashcardSet flashcardSet) {
        executor.execute(() -> flashcardSetDao.insert(flashcardSet));
    }

    public void insertFlashcards(FlashcardSet... flashcardSets) {
        executor.execute(() -> flashcardSetDao.insertAll(flashcardSets));
    }

    public void updateFlashcard(FlashcardSet flashcardSet) {
        executor.execute(() -> flashcardSetDao.update(flashcardSet));
    }

    public void deleteFlashcard(FlashcardSet flashcardSet) {
        executor.execute(() -> flashcardSetDao.delete(flashcardSet));
    }

    public void insertFlashcardSetWithFlashcards(FlashcardSet flashcardSet, List<Flashcard> flashcards) {
        executor.execute(() -> {
            long flashcardSetId = flashcardSetDao.insert(flashcardSet);
            for (Flashcard flashcard : flashcards) {
                long flashcardId = flashcardDao.insert(flashcard);
                flashcardSetDao.insertFlashcardSetCrossRef(new FlashcardSetCrossRef(flashcardId, flashcardSetId));
            }
        });
    }

    public void deleteFlashcardSetWithFlashcards(FlashcardSetWithFlashcards flashcardSetWithFlashcards) {
        executor.execute(() -> {
            flashcardSetDao.deleteFlashcardsInSet(flashcardSetWithFlashcards.flashcardSet.getFlashcardSetId());
            flashcardSetDao.deleteFlashcardSetCrossRef(flashcardSetWithFlashcards.flashcardSet.getFlashcardSetId());
            flashcardSetDao.delete(flashcardSetWithFlashcards.flashcardSet);
        });
    }

    public void addFlashcardToSet(Flashcard flashcard, long flashcardSetId) {
        executor.execute(() -> {
            long flashcardId = flashcardDao.insert(flashcard);
            flashcardSetDao.insertFlashcardSetCrossRef(
                    new FlashcardSetCrossRef(flashcardId, flashcardSetId)
            );
        });
    }


    public LiveData<FlashcardSetWithFlashcards> getFlashcardSetWithFlashcards(long setId) {
        return flashcardSetDao.getFlashcardSetWithFlashcards(setId);
    }
}
