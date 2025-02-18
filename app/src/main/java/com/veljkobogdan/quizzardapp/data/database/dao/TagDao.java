package com.veljkobogdan.quizzardapp.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.veljkobogdan.quizzardapp.data.database.entities.Tag;

import java.util.List;

@Dao
public interface TagDao {
    @Query("SELECT * FROM tag")
    LiveData<List<Tag>> getAll();

    @Query("SELECT * FROM tag WHERE name = :tagName LIMIT 1")
    Tag getTagByName(String tagName);

    @Insert
    void insertAll(Tag... tags);

    @Insert
    long insert(Tag tag);

    @Delete
    void delete(Tag tag);

    @Update
    void update(Tag tag);

    @Query("DELETE FROM tag")
    void deleteAll();
}
