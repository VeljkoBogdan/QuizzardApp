package com.veljkobogdan.quizzardapp.data.models;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Junction;
import androidx.room.Relation;

import com.veljkobogdan.quizzardapp.data.database.entities.Note;
import com.veljkobogdan.quizzardapp.data.database.entities.NoteTagCrossRef;
import com.veljkobogdan.quizzardapp.data.database.entities.Tag;

import java.util.List;

public class NoteWithTags {
    @Embedded
    public Note note;

    @Relation(
            parentColumn = "noteId",
            entityColumn = "tagId",
            associateBy = @Junction(NoteTagCrossRef.class)
    )
    public List<Tag> tags;

    public NoteWithTags() {}

    @Ignore
    public NoteWithTags(Note note, List<Tag> tags) {
        this.note = note;
        this.tags = tags;
    }
}
