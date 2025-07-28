package com.veljkobogdan.quizzardapp.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.veljkobogdan.quizzardapp.data.database.AppDatabase;
import com.veljkobogdan.quizzardapp.data.database.dao.ScheduleDao;
import com.veljkobogdan.quizzardapp.data.database.entities.Schedule;
import com.veljkobogdan.quizzardapp.data.database.entities.ScheduleWithSubjectsCrossRef;
import com.veljkobogdan.quizzardapp.data.database.entities.Subject;
import com.veljkobogdan.quizzardapp.data.models.ScheduleWithSubjects;

import java.time.DayOfWeek;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScheduleRepository {
    private ScheduleDao scheduleDao;
    private final ExecutorService executor;

    public ScheduleRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.scheduleDao = db.scheduleDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public void insertSubject(Subject subject) {
        executor.execute(() -> scheduleDao.insertSubject(subject));
    }

    public void insertScheduleEntry(Schedule schedule) {
        executor.execute(() -> scheduleDao.insertSchedule(schedule));
    }

    public void deleteScheduleEntry(Schedule entry) {
        executor.execute(() -> {
            scheduleDao.deleteSubjectsInSchedule(entry.scheduleId);
            scheduleDao.deleteScheduleEntry(entry);
        });
    }

    public void deleteSubject(Subject subject) {
        executor.execute(() -> scheduleDao.deleteSubject(subject));
    }

    public LiveData<List<Subject>> getAllSubjects() {
        return scheduleDao.getAllSubjects();
    }

    public LiveData<List<ScheduleWithSubjects>> getAllSchedulesWithSubjects() {
        return scheduleDao.getAllSchedulesWithSubjects();
    }

    public void insertScheduleWithSubjects(Schedule schedule, List<Subject> subjects) {
        executor.execute(() -> {
            long scheduleId = scheduleDao.insertSchedule(schedule);
            for (Subject subject : subjects) {
                long subjectId = scheduleDao.insertSubject(subject);
                scheduleDao.insertScheduleWithSubjectCrossRef(new ScheduleWithSubjectsCrossRef(scheduleId, subjectId));
            }
        });
    }

    public void deleteScheduleWithSubjects(ScheduleWithSubjects scheduleWithSubjects) {
        executor.execute(() -> {
            scheduleDao.deleteSubjectsInSchedule(scheduleWithSubjects.entry.scheduleId);
            scheduleDao.deleteScheduleWithSubjectsCrossRef(scheduleWithSubjects.entry.scheduleId);
            scheduleDao.deleteScheduleEntry(scheduleWithSubjects.entry);
        });
    }

    public void addSubjectToSchedule(Subject subject, long scheduleId) {
        executor.execute(() -> {
            long subjectId = scheduleDao.insertSubject(subject);
            scheduleDao.insertScheduleWithSubjectCrossRef(
                    new ScheduleWithSubjectsCrossRef(scheduleId, subjectId)
            );
        });
    }

    public LiveData<Subject> getSubject(long subjectId) {
        return scheduleDao.getSubject(subjectId);
    }

    public void updateSubject(Subject subject) {
        executor.execute(() -> scheduleDao.updateSubject(subject));
    }

    public void updateSchedule(Schedule schedule) {
        executor.execute(() -> scheduleDao.updateSchedule(schedule));
    }
}
