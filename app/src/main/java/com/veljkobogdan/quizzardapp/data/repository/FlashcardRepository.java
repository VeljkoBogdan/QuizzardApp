package com.veljkobogdan.quizzardapp.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.veljkobogdan.quizzardapp.data.database.AppDatabase;
import com.veljkobogdan.quizzardapp.data.database.dao.FlashcardDao;
import com.veljkobogdan.quizzardapp.data.database.dao.FlashcardSetDao;
import com.veljkobogdan.quizzardapp.data.database.entities.Flashcard;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FlashcardRepository {
    private final FlashcardDao flashcardDao;
    private final ExecutorService executor;

    FlashcardRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.flashcardDao = db.flashcardDao();
        this.executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Flashcard>> getAllFlashcards() {
        return flashcardDao.getFlashcards();
    }

    public void insertFlashcard(Flashcard flashcard) {
        executor.execute(() -> flashcardDao.insert(flashcard));
    }

    public void insertFlashcards(Flashcard... flashcards) {
        executor.execute(() -> flashcardDao.insertAll(flashcards));
    }

    public void updateFlashcard(Flashcard flashcard) {
        executor.execute(() -> flashcardDao.update(flashcard));
    }

    public void deleteFlashcard(Flashcard flashcard) {
        executor.execute(() -> flashcardDao.delete(flashcard));
    }
}
