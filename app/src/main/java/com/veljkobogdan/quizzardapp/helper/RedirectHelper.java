package com.veljkobogdan.quizzardapp.helper;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.annotation.StringDef;

import com.veljkobogdan.quizzardapp.MainActivity;
import com.veljkobogdan.quizzardapp.activity.calendar.CalendarDayView;
import com.veljkobogdan.quizzardapp.activity.calendar.NewCalendarInsertView;
import com.veljkobogdan.quizzardapp.activity.note.NewNoteActivity;
import com.veljkobogdan.quizzardapp.activity.note.NotesListActivity;

public interface RedirectHelper {
    static void toActivity(Context context, Class<?> activity, int flag) {
        Intent intent = new Intent(context, activity);
        if (flag != 0) {
            intent.addFlags(flag);
        }
        context.startActivity(intent);
    }

    static void toMainActivity(Context context, int flag) {
        toActivity(context, MainActivity.class, flag);
    }

    static void toNotesListActivity(Context context, int flag) {
        toActivity(context, NotesListActivity.class, flag);
    }

    static void toNewNoteActivity(Context context, int flag) {
        toActivity(context, NewNoteActivity.class, flag);
    }

    static void toNewCalendarInsertView(CalendarDayView calendarDayView, String string) {
        Intent intent = new Intent(calendarDayView, NewCalendarInsertView.class);
        intent.putExtra("date", string);
        calendarDayView.startActivity(intent);
    }
}
