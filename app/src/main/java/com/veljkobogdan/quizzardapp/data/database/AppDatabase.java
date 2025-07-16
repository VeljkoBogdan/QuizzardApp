package com.veljkobogdan.quizzardapp.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.veljkobogdan.quizzardapp.data.database.converter.LocalDateConverter;
import com.veljkobogdan.quizzardapp.data.database.converter.LocalDateTimeConverter;
import com.veljkobogdan.quizzardapp.data.database.converter.ScheduleConverters;
import com.veljkobogdan.quizzardapp.data.database.dao.CalendarEntryDao;
import com.veljkobogdan.quizzardapp.data.database.dao.ExamDao;
import com.veljkobogdan.quizzardapp.data.database.dao.FlashcardDao;
import com.veljkobogdan.quizzardapp.data.database.dao.FlashcardSetDao;
import com.veljkobogdan.quizzardapp.data.database.dao.NoteDao;
import com.veljkobogdan.quizzardapp.data.database.dao.QuestionDao;
import com.veljkobogdan.quizzardapp.data.database.dao.ScheduleDao;
import com.veljkobogdan.quizzardapp.data.database.dao.TagDao;
import com.veljkobogdan.quizzardapp.data.database.entities.CalendarEntry;
import com.veljkobogdan.quizzardapp.data.database.entities.Exam;
import com.veljkobogdan.quizzardapp.data.database.entities.ExamQuestionsCrossRef;
import com.veljkobogdan.quizzardapp.data.database.entities.Flashcard;
import com.veljkobogdan.quizzardapp.data.database.entities.FlashcardSet;
import com.veljkobogdan.quizzardapp.data.database.entities.FlashcardSetCrossRef;
import com.veljkobogdan.quizzardapp.data.database.entities.Note;
import com.veljkobogdan.quizzardapp.data.database.entities.NoteTagCrossRef;
import com.veljkobogdan.quizzardapp.data.database.entities.Question;
import com.veljkobogdan.quizzardapp.data.database.entities.Schedule;
import com.veljkobogdan.quizzardapp.data.database.entities.ScheduleWithSubjectsCrossRef;
import com.veljkobogdan.quizzardapp.data.database.entities.Subject;
import com.veljkobogdan.quizzardapp.data.database.entities.Tag;

@Database(entities = {
        Note.class,
        Tag.class,
        NoteTagCrossRef.class,
        Flashcard.class,
        FlashcardSet.class,
        FlashcardSetCrossRef.class,
        Question.class,
        Exam.class,
        ExamQuestionsCrossRef.class,
        CalendarEntry.class,
        Schedule.class,
        Subject.class,
        ScheduleWithSubjectsCrossRef.class
}, version = 24)
@TypeConverters({
        LocalDateConverter.class,
        LocalDateTimeConverter.class,
        ScheduleConverters.class
})
public abstract class AppDatabase extends RoomDatabase {
    public static AppDatabase db;
    public static String name = "quizzard";

    public synchronized static AppDatabase getInstance(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, name)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return db;
    }

    public abstract NoteDao noteDao();
    public abstract TagDao tagDao();
    public abstract FlashcardDao flashcardDao();
    public abstract FlashcardSetDao flashcardSetDao();
    public abstract QuestionDao questionDao();
    public abstract ExamDao examDao();
    public abstract CalendarEntryDao calendarEntryDao();
    public abstract ScheduleDao scheduleDao();
}
