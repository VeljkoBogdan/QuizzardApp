package com.veljkobogdan.quizzardapp.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.veljkobogdan.quizzardapp.data.database.AppDatabase;
import com.veljkobogdan.quizzardapp.data.database.dao.NoteDao;
import com.veljkobogdan.quizzardapp.data.database.dao.TagDao;
import com.veljkobogdan.quizzardapp.data.database.entities.Note;
import com.veljkobogdan.quizzardapp.data.database.entities.NoteTagCrossRef;
import com.veljkobogdan.quizzardapp.data.database.entities.Tag;
import com.veljkobogdan.quizzardapp.data.models.NoteWithTags;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {
    private final NoteDao noteDao;
    private final TagDao tagDao;
    private final ExecutorService executor;

    public NoteRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        noteDao = db.noteDao();
        tagDao = db.tagDao();
        executor = Executors.newSingleThreadExecutor(); // Background Operations
    }

    public LiveData<List<Note>> getAllNotes() {
        return noteDao.getAll();
    }

    public LiveData<List<NoteWithTags>> getAllNotesWithTags() {
        return noteDao.getNotesWithTags();
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

    public void insertNoteWithTags(Note note, List<Tag> tags) {
        executor.execute(() -> {
            long noteId = noteDao.insert(note);
            for (Tag tag : tags) {
                Tag existingTag = tagDao.getTagByName(tag.getName());
                if (existingTag == null) {
                    long tagId = tagDao.insert(tag);
                    existingTag = tag;
                }
                noteDao.insertNoteTagCrossRef(new NoteTagCrossRef(noteId, existingTag.getTagId()));
            }
        });
    }
}
