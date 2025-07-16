package com.veljkobogdan.quizzardapp.ui.schedule;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Schedule;
import com.veljkobogdan.quizzardapp.data.database.entities.Subject;
import com.veljkobogdan.quizzardapp.data.repository.ScheduleRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityAddSubjectBinding;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddSubjectActivity extends AppCompatActivity {

    ActivityAddSubjectBinding binding;
    ScheduleRepository scheduleRepository;
    Schedule schedule;
    Spinner startHourSpinner, startMinuteSpinner, endHourSpinner, endMinuteSpinner, dayOfWeekSpinner;
    EditText subjectNameEditText, teacherNameEditText, locationEditText;
    Button addSubjectButton;

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

        subjectNameEditText = binding.subjectName;
        teacherNameEditText = binding.teacherName;
        locationEditText = binding.location;
        dayOfWeekSpinner = binding.dayOfWeekSpinner;
        addSubjectButton = binding.addSubjectButton;
        startHourSpinner = binding.startHourSpinner;
        startMinuteSpinner = binding.startMinuteSpinner;
        endHourSpinner = binding.endHourSpinner;
        endMinuteSpinner = binding.endMinuteSpinner;

        getIntentContent();
        setupTimeSpinners();

        addSubjectButton.setOnClickListener(view -> {
            String name = subjectNameEditText.getText().toString().trim();
            String teacherName = teacherNameEditText.getText().toString().trim();
            String location = locationEditText.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, "Subject must have a name", Toast.LENGTH_SHORT).show();
                return;
            }

            int startHour = (int) startHourSpinner.getSelectedItem();
            int startMinute = (int) startMinuteSpinner.getSelectedItem();
            int endHour = (int) endHourSpinner.getSelectedItem();
            int endMinute = (int) endMinuteSpinner.getSelectedItem();

            String selectedDay = dayOfWeekSpinner.getSelectedItem().toString();
            DayOfWeek dayOfWeek = DayOfWeek.valueOf(selectedDay.toUpperCase());

            Subject subject = new Subject();
            subject.name = name;
            subject.teacherName = teacherName;
            subject.location = location;
            subject.dayOfWeek = dayOfWeek;
            subject.startTime = LocalTime.of(startHour, startMinute);
            subject.endTime = LocalTime.of(endHour, endMinute);

            if (subject.endTime.isBefore(subject.startTime) || subject.endTime.equals(subject.startTime)) {
                Toast.makeText(this, "End time must be after start time", Toast.LENGTH_SHORT).show();
                return;
            }

            scheduleRepository.addSubjectToSchedule(subject, schedule.scheduleId);
            finish();
        });
    }

    private void setupTimeSpinners() {
        List<Integer> hours = new ArrayList<>();
        for (int i = 6; i <= 22; i++) hours.add(i);

        List<Integer> minutes = Arrays.asList(0, 15, 30, 45);

        ArrayAdapter<Integer> hourAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hours);
        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<Integer> minuteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, minutes);
        minuteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        startHourSpinner.setAdapter(hourAdapter);
        startMinuteSpinner.setAdapter(minuteAdapter);
        endHourSpinner.setAdapter(hourAdapter);
        endMinuteSpinner.setAdapter(minuteAdapter);

        // day of week
        ArrayAdapter<DayOfWeek> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                DayOfWeek.values()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayOfWeekSpinner.setAdapter(adapter);

    }

    private void getIntentContent() {
        schedule = (Schedule) getIntent().getSerializableExtra(IntentGroup.SCHEDULE);
    }
}