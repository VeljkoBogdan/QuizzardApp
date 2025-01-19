package com.veljkobogdan.quizzardapp.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.veljkobogdan.quizzardapp.data.database.entities.Note;
import com.veljkobogdan.quizzardapp.data.database.entities.NoteTagCrossRef;
import com.veljkobogdan.quizzardapp.data.models.NoteWithTags;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    LiveData<List<Note>> getAll();

    @Insert
    void insertAll(Note... notes);

    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Update
    void update(Note note);

    @Transaction
    @Query("SELECT * FROM note")
    List<NoteWithTags> getNotesWithTags();
}
