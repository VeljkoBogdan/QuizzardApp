package com.veljkobogdan.quizzardapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.veljkobogdan.quizzardapp.entities.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    List<Note> getAll();

    @Insert
    void insertAll(Note... notes);

    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);
}
