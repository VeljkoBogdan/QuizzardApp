package com.veljkobogdan.quizzardapp.ui.calendar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.kizitonwose.calendar.core.CalendarDay;
import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.databinding.ActivityDayViewBinding;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

import java.time.format.DateTimeFormatter;

public class DayViewActivity extends AppCompatActivity {
    private ActivityDayViewBinding binding;
    private CalendarDay day;
    private MaterialButton addEventButton;
    private LinearLayout calendarEntryLayout;

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

        this.addEventButton = binding.addEventButton;
        this.calendarEntryLayout = binding.calendarEntryLayout;
    }

    private void getIntentContent() {
        this.day = (CalendarDay) getIntent().getSerializableExtra(IntentGroup.DAY);
    }
}