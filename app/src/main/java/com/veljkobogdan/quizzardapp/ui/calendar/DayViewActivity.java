package com.veljkobogdan.quizzardapp.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.kizitonwose.calendar.core.CalendarDay;
import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.CalendarEntry;
import com.veljkobogdan.quizzardapp.data.repository.CalendarEntryRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityDayViewBinding;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DayViewActivity extends AppCompatActivity {
    private ActivityDayViewBinding binding;
    private CalendarDay day;
    private MaterialButton addEventButton;
    private LinearLayout calendarEntryLayout;
    private List<CalendarEntry> entries = new ArrayList<>();
    private CalendarEntryRepository calendarEntryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityDayViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getIntentContent();

        Toolbar toolbar = binding.toolbarIncl.toolbar;
        toolbar.setTitle(day.getDate().format(DateTimeFormatter.ofPattern("MMMM d yyyy")));
        setSupportActionBar(toolbar);

        calendarEntryRepository = new CalendarEntryRepository(this);

        this.addEventButton = binding.addEventButton;
        this.calendarEntryLayout = binding.calendarEntryLayout;

        this.addEventButton.setOnClickListener(view -> {
            Intent i = new Intent(DayViewActivity.this, NewEventActivity.class);
            i.putExtra(IntentGroup.DAY, day);
            startActivity(i);
        });

        getEntries();
    }

    private void getIntentContent() {
        this.day = (CalendarDay) getIntent().getSerializableExtra(IntentGroup.DAY);
    }

    private void getEntries() {
        calendarEntryRepository.getCalendarEntriesForDay(day).observe(this, updatedEntries -> {
            if (!updatedEntries.isEmpty()) {
                entries = updatedEntries;
                populateEntryLayout();
            }
        });
    }

    private void populateEntryLayout() {
        TextView eventTitle, eventTime;

        calendarEntryLayout.removeAllViews();
        for (CalendarEntry calendarEntry : entries) {
            View card = LayoutInflater.from(this).inflate(R.layout.item_event, binding.getRoot(), false);
            eventTitle = card.findViewById(R.id.eventName);
            eventTime = card.findViewById(R.id.eventTime);

            eventTitle.setText(calendarEntry.title);

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d MMM, yyyy 'at' HH:mm");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMM, yyyy");

            String formattedTime;
            if (calendarEntry.localDateTime != null) {
                formattedTime = calendarEntry.localDateTime.format(dateTimeFormatter);
            } else {
                formattedTime = calendarEntry.localDate.format(dateFormatter);
            }
            eventTime.setText(formattedTime);

            calendarEntryLayout.addView(card);
        }
    }
}