package com.veljkobogdan.quizzardapp.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.veljkobogdan.quizzardapp.data.database.AppDatabase;
import com.veljkobogdan.quizzardapp.data.database.dao.TagDao;
import com.veljkobogdan.quizzardapp.data.database.entities.Tag;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TagRepository {
    private final TagDao tagDao;
    private final ExecutorService executor;

    public TagRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        tagDao = db.tagDao();
        executor = Executors.newSingleThreadExecutor(); // Background Operations
    }

    public LiveData<List<Tag>> getAllTags() {
        return tagDao.getAll();
    }

    public void insertTag(Tag tag) {
        executor.execute(() -> {
            if (tagDao.getTagByName(tag.getName()) == null) {
                tagDao.insert(tag);
            }
        });
    }

    public void deleteTag(Tag tag) {
        executor.execute(() -> tagDao.delete(tag));
    }

    public void updateTag(Tag tag) {
        executor.execute(() -> tagDao.update(tag));
    }

}