package com.veljkobogdan.quizzardapp.ui.schedule;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Schedule;
import com.veljkobogdan.quizzardapp.data.repository.ScheduleRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityAddSubjectBinding;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddSubjectActivity extends AppCompatActivity {

    ActivityAddSubjectBinding binding;
    ScheduleRepository scheduleRepository;
    Schedule schedule;
    Spinner startHour, startMinute, endHour, endMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityAddSubjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        scheduleRepository = new ScheduleRepository(this);

        getIntentContent();
        setupTimeSpinners();
    }

    private void setupTimeSpinners() {
        List<Integer> hours = new ArrayList<>();
        for (int i = 6; i <= 22; i++) hours.add(i);

        List<Integer> minutes = Arrays.asList(0, 15, 30, 45);

        ArrayAdapter<Integer> hourAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hours);
        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<Integer> minuteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, minutes);
        minuteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        startHour = binding.startHourSpinner;
        startMinute = binding.startMinuteSpinner;
        endHour = binding.endHourSpinner;
        endMinute = binding.endMinuteSpinner;

        startHour.setAdapter(hourAdapter);
        startMinute.setAdapter(minuteAdapter);
        endHour.setAdapter(hourAdapter);
        endMinute.setAdapter(minuteAdapter);
    }

    private void getIntentContent() {
        schedule = (Schedule) getIntent().getSerializableExtra(IntentGroup.SCHEDULE);
    }
}