package com.veljkobogdan.quizzardapp.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.veljkobogdan.quizzardapp.entity.Note;

import java.util.List;

@Dao
public interface NoteDAO {

    @Insert(onConflict = REPLACE)
    void insert(Note note);

    @Query("SELECT * FROM notes\n" +
            "ORDER BY \n" +
            "  CASE WHEN pin = 1 THEN 0 ELSE 1 END,\n" +
            "  id DESC;")
    List<Note> getAll();

    @Query("UPDATE notes SET title = :title, text = :text, date = :date, pin = :isPinned WHERE id = :id")
    void update(int id, String title, String text, String date, boolean isPinned);

    @Delete
    void delete(Note note);
}
