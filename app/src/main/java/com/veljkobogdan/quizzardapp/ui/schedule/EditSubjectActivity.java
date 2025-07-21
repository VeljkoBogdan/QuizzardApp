package com.veljkobogdan.quizzardapp.ui.schedule;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Subject;
import com.veljkobogdan.quizzardapp.data.repository.ScheduleRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityEditSubjectBinding;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class EditSubjectActivity extends AppCompatActivity {

    ActivityEditSubjectBinding binding;
    Subject subject;
    EditText subjectName, teacherName, location;
    Spinner dayOfWeekSpinner, startHourSpinner, startMinuteSpinner, endHourSpinner, endMinuteSpinner;
    Button colorPickerButton, saveSubjectButton;
    int cardColor;
    ScheduleRepository scheduleRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityEditSubjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        scheduleRepository = new ScheduleRepository(this);

        // Setup toolbar
        Toolbar toolbar = binding.toolbarIncl.toolbar;
        toolbar.setTitle("Edit Subject");
        setSupportActionBar(toolbar);

        // Field bindings
        subjectName = binding.subjectName;
        teacherName = binding.teacherName;
        location = binding.location;
        dayOfWeekSpinner = binding.dayOfWeekSpinner;
        startHourSpinner = binding.startHourSpinner;
        startMinuteSpinner = binding.startMinuteSpinner;
        endHourSpinner = binding.endHourSpinner;
        endMinuteSpinner = binding.endMinuteSpinner;
        colorPickerButton = binding.colorPickerButton;
        saveSubjectButton = binding.saveSubjectButton;

        getIntentContent();
        setupDayOfWeekSpinner();
        setupTimeSpinners();
        setupInfo();

        binding.saveSubjectButton.setOnClickListener(view -> {
            String name = subjectName.getText().toString().trim();
            String teacherNameString = teacherName.getText().toString().trim();
            String locationName = location.getText().toString().trim();

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
            subject.teacherName = teacherNameString;
            subject.location = locationName;
            subject.dayOfWeek = dayOfWeek;
            subject.startTime = LocalTime.of(startHour, startMinute);
            subject.endTime = LocalTime.of(endHour, endMinute);
            subject.color = cardColor;

            if (subject.endTime.isBefore(subject.startTime) || subject.endTime.equals(subject.startTime)) {
                Toast.makeText(this, "End time must be after start time", Toast.LENGTH_SHORT).show();
                return;
            }

            scheduleRepository.updateSubject(subject);
            finish();
        });

        colorPickerButton.setOnClickListener(view -> {
            AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, cardColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                @Override
                public void onOk(AmbilWarnaDialog dialog, int color) {
                    cardColor = color;
                }

                @Override
                public void onCancel(AmbilWarnaDialog dialog) {}
            });
            colorPicker.show();
        });
    }

    private void getIntentContent() {
        subject = (Subject) getIntent().getSerializableExtra(IntentGroup.SUBJECT);
    }

    private void setupInfo() {
        subjectName.setText(subject.name);
        teacherName.setText(subject.teacherName);
        location.setText(subject.location);

        String dayName = subject.dayOfWeek.name().substring(0, 1).toUpperCase() +
                subject.dayOfWeek.name().substring(1).toLowerCase();
        setSpinnerToValue(dayOfWeekSpinner, dayName);

        int color = subject.color;
        colorPickerButton.setBackgroundTintList(android.content.res.ColorStateList.valueOf(color));
    }

    private void setupDayOfWeekSpinner() {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayOfWeekSpinner.setAdapter(dayAdapter);
    }

    private void setupTimeSpinners() {
        ArrayAdapter<String> hourAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        ArrayAdapter<String> minuteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);

        for (int i = 0; i < 24; i++) hourAdapter.add(String.format("%02d", i));
        for (int i = 0; i < 60; i += 5) minuteAdapter.add(String.format("%02d", i));

        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minuteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        startHourSpinner.setAdapter(hourAdapter);
        endHourSpinner.setAdapter(hourAdapter);
        startMinuteSpinner.setAdapter(minuteAdapter);
        endMinuteSpinner.setAdapter(minuteAdapter);

        int startHour = subject.startTime.getHour();
        int startMinute = subject.startTime.getMinute();
        int endHour = subject.endTime.getHour();
        int endMinute = subject.endTime.getMinute();

        String startHourStr = String.format("%02d", startHour);
        String startMinuteStr = String.format("%02d", startMinute);
        String endHourStr = String.format("%02d", endHour);
        String endMinuteStr = String.format("%02d", endMinute);

        setSpinnerToValue(startHourSpinner, startHourStr);
        setSpinnerToValue(startMinuteSpinner, startMinuteStr);
        setSpinnerToValue(endHourSpinner, endHourStr);
        setSpinnerToValue(endMinuteSpinner, endMinuteStr);
    }

    private void setSpinnerToValue(Spinner spinner, String value) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(value);
            if (position >= 0) {
                spinner.setSelection(position);
            }
        }
    }
}
