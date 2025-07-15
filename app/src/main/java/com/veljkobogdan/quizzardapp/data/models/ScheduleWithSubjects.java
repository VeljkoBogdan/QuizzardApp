package com.veljkobogdan.quizzardapp.data.models;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Relation;

import com.veljkobogdan.quizzardapp.data.database.entities.Schedule;
import com.veljkobogdan.quizzardapp.data.database.entities.Subject;

import java.util.List;

public class ScheduleWithSubjects {
    @Embedded
    public Schedule entry;

    @Relation(
            parentColumn = "scheduleId",
            entityColumn = "subjectId"
    )
    public List<Subject> subjectList;

    public ScheduleWithSubjects() {}

    @Ignore
    public ScheduleWithSubjects(Schedule schedule, List<Subject> subjects) {
        this.entry = schedule;
        this.subjectList = subjects;
    }
}

