package com.veljkobogdan.quizzardapp.helper;

import android.content.Context;
import android.content.Intent;

import com.veljkobogdan.quizzardapp.MainActivity;
import com.veljkobogdan.quizzardapp.activity.note.NewNoteActivity;
import com.veljkobogdan.quizzardapp.activity.note.NotesListActivity;

public interface RedirectHelper {
    static void toActivity(Context context, Class<?> activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }

    static void toMainActivity(Context context) {
        toActivity(context, MainActivity.class);
    }

    static void toNotesListActivity(Context context) {
        toActivity(context, NotesListActivity.class);
    }

    static void toNewNoteActivity(Context context) {
        toActivity(context, NewNoteActivity.class);
    }
}
