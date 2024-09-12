package com.veljkobogdan.quizzardapp.activity.calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.adapter.DayAdapter;
import com.veljkobogdan.quizzardapp.entity.CalendarInsert;
import com.veljkobogdan.quizzardapp.helper.DateTimeFormatterHelper;
import com.veljkobogdan.quizzardapp.helper.RedirectHelper;
import com.veljkobogdan.quizzardapp.listener.CalendarInsertClickListener;
import com.veljkobogdan.quizzardapp.repository.CalendarInsertRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CalendarDayView extends AppCompatActivity {
    private RecyclerView dayRecyclerView;
    private DayAdapter dayAdapter;
    private LocalDate selectedDate;
    private ImageButton image_back, image_add;
    private final CalendarInsertClickListener calendarInsertClickListener =
            new CalendarInsertClickListener() {
        @Override
        public void onClick(CalendarInsert calendarInsert) {
            Log.i("CLICKED", calendarInsert.toString());
        }

        @Override
        public void onLongClick(CalendarInsert calendarInsert, CardView cardView) {
            Log.i("LONG_CLICKED", calendarInsert.toString());
        }
    };

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_view);

        try {
            initWidgets();

            dayRecyclerView = findViewById(R.id.day_recycler_view);
            // Parse to LocalDate
            DateTimeFormatter formatter = DateTimeFormatterHelper.getCalendarFormat();
            selectedDate = LocalDate.parse(getIntent().getStringExtra("date"), formatter);
            // Add onclick to image_add
            image_add.setOnClickListener(v -> RedirectHelper
                    .toNewCalendarInsertView(this, selectedDate.toString()));

            // Update recycler
            updateRecycler();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            RedirectHelper.toMainActivity(this, 0);
        }
    }

    @SuppressLint("NewApi")
    private void updateRecycler() {
        try {
            dayRecyclerView.setHasFixedSize(true);
            dayRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,
                    StaggeredGridLayoutManager.VERTICAL));

            List<CalendarInsert> calendarInserts = CalendarInsertRepository
                    .getCalendarInsertsForDay(selectedDate, this);

            // Set title
            TextView title = findViewById(R.id.title);
            title.setText(selectedDate.format(DateTimeFormatterHelper.getCalendarFormat()));
            title.setVisibility(View.VISIBLE);

            dayAdapter = new DayAdapter(CalendarDayView.this, calendarInserts,
                    calendarInsertClickListener);
            dayRecyclerView.setAdapter(dayAdapter);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void initWidgets() {
        image_add = findViewById(R.id.image_add);
        image_back = findViewById(R.id.image_back);
        image_back.setOnClickListener(v -> {
            RedirectHelper.toMainActivity(this, 0);
            CalendarDayView.this.finish();
        });
    }

}