package com.veljkobogdan.quizzardapp.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.veljkobogdan.quizzardapp.data.database.AppDatabase;
import com.veljkobogdan.quizzardapp.data.database.dao.NoteDao;
import com.veljkobogdan.quizzardapp.data.database.entities.Note;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {
    private final NoteDao noteDao;
    private final ExecutorService executor;

    public NoteRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        noteDao = db.noteDao();
        executor = Executors.newSingleThreadExecutor(); // Background Operations
    }

    public LiveData<List<Note>> getAllNotes() {
        return noteDao.getAll();
    }

    public void insertNote(Note note) {
        executor.execute(() -> noteDao.insert(note));
    }

    public void deleteNote(Note note) {
        executor.execute(() -> noteDao.delete(note));
    }

    public void updateNote(Note note) {
        executor.execute(() -> noteDao.update(note));
    }

}
