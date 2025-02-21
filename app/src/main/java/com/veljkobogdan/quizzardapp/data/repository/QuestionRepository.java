package com.veljkobogdan.quizzardapp.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.veljkobogdan.quizzardapp.data.database.AppDatabase;
import com.veljkobogdan.quizzardapp.data.database.dao.QuestionDao;
import com.veljkobogdan.quizzardapp.data.database.entities.Question;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuestionRepository {
    private final QuestionDao questionDao;
    private final ExecutorService executor;

    public QuestionRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.questionDao = db.questionDao();
        this.executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Question>> getAllQuestions() {
        return questionDao.getQuestions();
    }

    public void insert(Question question) {
        executor.execute(() -> questionDao.insert(question));
    }

    public void insertAll(Question... questions) {
        executor.execute(() -> questionDao.insertAll(questions));
    }

    public void update(Question question) {
        executor.execute(() -> questionDao.update(question));
    }

    public void delete(Question question) {
        executor.execute(() -> questionDao.delete(question));
    }
}
