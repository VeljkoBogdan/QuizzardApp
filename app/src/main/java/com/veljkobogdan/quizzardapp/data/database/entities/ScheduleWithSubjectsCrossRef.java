package com.veljkobogdan.quizzardapp.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.io.Serializable;

@Entity(primaryKeys = {"scheduleId", "subjectId"})
public class ScheduleWithSubjectsCrossRef implements Serializable {
    @ColumnInfo
    public long scheduleId;
    @ColumnInfo
    public long subjectId;

    public ScheduleWithSubjectsCrossRef() {}

    @Ignore
    public ScheduleWithSubjectsCrossRef(long scheduleId, long subjectId) {
        this.scheduleId = scheduleId;
        this.subjectId = subjectId;
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }
}
