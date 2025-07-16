package com.veljkobogdan.quizzardapp.data.models;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Junction;
import androidx.room.Relation;

import com.veljkobogdan.quizzardapp.data.database.entities.NoteTagCrossRef;
import com.veljkobogdan.quizzardapp.data.database.entities.Schedule;
import com.veljkobogdan.quizzardapp.data.database.entities.ScheduleWithSubjectsCrossRef;
import com.veljkobogdan.quizzardapp.data.database.entities.Subject;

import java.util.List;

public class ScheduleWithSubjects {
    @Embedded
    public Schedule entry;

    @Relation(
            parentColumn = "scheduleId",
            entityColumn = "subjectId",
            associateBy = @Junction(ScheduleWithSubjectsCrossRef.class)
    )
    public List<Subject> subjectList;

    public ScheduleWithSubjects() {}

    @Ignore
    public ScheduleWithSubjects(Schedule schedule, List<Subject> subjects) {
        this.entry = schedule;
        this.subjectList = subjects;
    }
}

