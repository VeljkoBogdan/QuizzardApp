package com.veljkobogdan.quizzardapp.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.veljkobogdan.quizzardapp.data.database.entities.Schedule;
import com.veljkobogdan.quizzardapp.data.database.entities.ScheduleWithSubjectsCrossRef;
import com.veljkobogdan.quizzardapp.data.database.entities.Subject;
import com.veljkobogdan.quizzardapp.data.models.ScheduleWithSubjects;

import java.time.DayOfWeek;
import java.util.List;

@Dao
public interface ScheduleDao {

    @Insert
    long insertSchedule(Schedule entry);

    @Insert
    long insertSubject(Subject subject);

    @Delete
    void deleteScheduleEntry(Schedule entry);

    @Delete
    void deleteSubject(Subject subject);

    @Query("SELECT * FROM subjects")
    LiveData<List<Subject>> getAllSubjects();

    @Insert
    void insertScheduleWithSubjectCrossRef(ScheduleWithSubjectsCrossRef scheduleWithSubjectsCrossRef);

    @Query("DELETE FROM schedulewithsubjectscrossref WHERE scheduleId = :scheduleId")
    void deleteScheduleWithSubjectsCrossRef(long scheduleId);

    @Query("DELETE FROM subjects WHERE subjectId IN (SELECT subjectId FROM schedulewithsubjectscrossref WHERE scheduleId = :scheduleId)")
    void deleteSubjectsInSchedule(long scheduleId);

    @Transaction
    @Query("SELECT * FROM schedule")
    LiveData<List<ScheduleWithSubjects>> getAllSchedulesWithSubjects();

    @Query("SELECT * FROM subjects WHERE subjectId = :subjectId")
    LiveData<Subject> getSubject(long subjectId);

    @Update
    void updateSubject(Subject subject);

    @Update
    void updateSchedule(Schedule schedule);
}

