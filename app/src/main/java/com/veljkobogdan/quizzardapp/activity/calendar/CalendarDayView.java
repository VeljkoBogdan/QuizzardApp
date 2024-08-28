package com.veljkobogdan.quizzardapp.activity.calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.adapter.DayAdapter;
import com.veljkobogdan.quizzardapp.database.CalendarDAO;
import com.veljkobogdan.quizzardapp.database.RoomDB;
import com.veljkobogdan.quizzardapp.entity.CalendarInsert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class CalendarDayView extends AppCompatActivity {

    private RecyclerView dayRecyclerView;
    private DayAdapter dayAdapter;
    private LocalDate selectedDate;
    private RoomDB database;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_view);

        try {
            database = RoomDB.getInstance(this);

            dayRecyclerView = findViewById(R.id.day_recycler_view);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy",
                    Locale.ENGLISH);
            selectedDate = LocalDate.parse(getIntent().getStringExtra("date"), formatter);
            // When a day is tapped, get the list of CalendarInsert entities for that day
            List<CalendarInsert> calendarInserts = getCalendarInsertsForDay(selectedDate);

            dayAdapter = new DayAdapter(calendarInserts);
            dayRecyclerView.setAdapter(dayAdapter);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private List<CalendarInsert> getCalendarInsertsForDay(LocalDate date) {
        try {
            return database.calendarDAO().getCalendarInsertsForDay(date.toString());
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }
}