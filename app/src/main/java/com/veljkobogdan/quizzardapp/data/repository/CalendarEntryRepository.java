package com.veljkobogdan.quizzardapp.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.veljkobogdan.quizzardapp.data.database.AppDatabase;
import com.veljkobogdan.quizzardapp.data.database.dao.CalendarEntryDao;
import com.veljkobogdan.quizzardapp.data.database.entities.CalendarEntry;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CalendarEntryRepository {
    private ExecutorService executor;
    private CalendarEntryDao calendarEntryDao;

    public CalendarEntryRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        calendarEntryDao = db.calendarEntryDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<CalendarEntry>> getCalendarEntries() {
        return calendarEntryDao.getCalendarEntries();
    }

    public void insertCalendarEntry(CalendarEntry entry) {
        executor.execute(() -> {
            calendarEntryDao.insert(entry);
        });
    }

    public void insertCalendarEntries(CalendarEntry... entries) {
        executor.execute(() -> {
            calendarEntryDao.insertAll(entries);
        });
    }

    public void updateCalendarEntry(CalendarEntry entry) {
        executor.execute(() -> {
            calendarEntryDao.update(entry);
        });
    }

    public void deleteCalendarEntry(CalendarEntry entry) {
        executor.execute(() -> {
            calendarEntryDao.delete(entry);
        });
    }
}
