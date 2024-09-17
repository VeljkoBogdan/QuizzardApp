package com.veljkobogdan.quizzardapp.helper;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentActivity;

import com.veljkobogdan.quizzardapp.MainActivity;
import com.veljkobogdan.quizzardapp.activity.calendar.CalendarDayView;
import com.veljkobogdan.quizzardapp.activity.calendar.NewCalendarInsertView;
import com.veljkobogdan.quizzardapp.activity.flashcard.CreateFlashcardActivity;
import com.veljkobogdan.quizzardapp.activity.flashcard.CreateFlashcardCollectionActivity;
import com.veljkobogdan.quizzardapp.activity.flashcard.FlashcardActivity;
import com.veljkobogdan.quizzardapp.activity.flashcard.FlashcardCollectionListView;
import com.veljkobogdan.quizzardapp.activity.flashcard.FlashcardCollectionView;
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

    static void toFlashcardCollectionListView(Context context, int flag) {
        toActivity(context, FlashcardCollectionListView.class, flag);
    }

    static void toNewFlashcardActivity(Context context, int flag) {
        toActivity(context, CreateFlashcardActivity.class, flag);
    }

    static void toFlashcardListView(Context context, int flag) {
        toActivity(context, FlashcardActivity.class, flag);
    }

    static void toNewFlashcardCollectionActivity(Context context, int flag) {
        toActivity(context, CreateFlashcardCollectionActivity.class, flag);
    }

    static void toFlashcardCollectionActivity(Context context, int flag) {
        toActivity(context, FlashcardCollectionView.class, flag);
    }
}
