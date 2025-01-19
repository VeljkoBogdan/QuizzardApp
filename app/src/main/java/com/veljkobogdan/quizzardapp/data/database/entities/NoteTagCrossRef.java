package com.veljkobogdan.quizzardapp.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(primaryKeys = {"noteId", "tagId"})
public class NoteTagCrossRef {
    @ColumnInfo
    public long noteId;
    @ColumnInfo
    public long tagId;

    public NoteTagCrossRef() {}

    @Ignore
    public NoteTagCrossRef(long noteId, long tagId) {
        this.noteId = noteId;
        this.tagId = tagId;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }
}
