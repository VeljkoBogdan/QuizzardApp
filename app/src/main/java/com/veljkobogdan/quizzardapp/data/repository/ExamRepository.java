package com.veljkobogdan.quizzardapp.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.veljkobogdan.quizzardapp.data.database.AppDatabase;
import com.veljkobogdan.quizzardapp.data.database.dao.ExamDao;
import com.veljkobogdan.quizzardapp.data.database.dao.QuestionDao;
import com.veljkobogdan.quizzardapp.data.database.entities.Exam;
import com.veljkobogdan.quizzardapp.data.database.entities.ExamQuestionsCrossRef;
import com.veljkobogdan.quizzardapp.data.database.entities.Flashcard;
import com.veljkobogdan.quizzardapp.data.database.entities.FlashcardSetCrossRef;
import com.veljkobogdan.quizzardapp.data.database.entities.Question;
import com.veljkobogdan.quizzardapp.data.models.ExamWithQuestions;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExamRepository {
    private final ExamDao examDao;
    private final QuestionDao questionDao;
    private final ExecutorService executor;

    public ExamRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        examDao = db.examDao();
        questionDao = db.questionDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Exam>> getExams() {
        return examDao.getExams();
    }

    public LiveData<List<ExamWithQuestions>> getExamsWithQuestions() {
        return examDao.getExamsWithQuestions();
    }

    public LiveData<ExamWithQuestions> getExamWithQuestions(long examId) {
        return examDao.getExamWithQuestions(examId);
    }

    public void insertExams(Exam... exams) {
        executor.execute(() -> examDao.insertAll(exams));
    }

    public void insertExam(Exam exam) {
        executor.execute(() -> examDao.insert(exam));
    }

    public void updateExam(Exam exam) {
        executor.execute(() -> examDao.update(exam));
    }

    public void deleteExam(Exam exam) {
        executor.execute(() -> examDao.delete(exam));
    }

    public void insertExamWithQuestions(Exam exam, List<Question> questions) {
        executor.execute(() -> {
            long examId = examDao.insert(exam);
            for (Question question : questions) {
                long questionId = questionDao.insert(question);
                examDao.insertExamQuestionCrossRef(new ExamQuestionsCrossRef(questionId, examId));
            }
        });
    }

    public void deleteExamWithQuestions(ExamWithQuestions examWithQuestions) {
        executor.execute(() -> {
            examDao.deleteQuestionsInExam(examWithQuestions.exam.getExamId());
            examDao.deleteExamQuestionCrossRef(examWithQuestions.exam.getExamId());
            examDao.delete(examWithQuestions.exam);
        });
    }

    public void addQuestionToExam(Question question, long examId) {
        executor.execute(() -> {
            long questionId = questionDao.insert(question);
            examDao.insertExamQuestionCrossRef(new ExamQuestionsCrossRef(questionId, examId));
        });
    }
}
