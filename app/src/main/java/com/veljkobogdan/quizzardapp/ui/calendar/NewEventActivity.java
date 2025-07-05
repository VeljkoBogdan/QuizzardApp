package com.veljkobogdan.quizzardapp.ui.calendar;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.veljkobogdan.quizzardapp.databinding.ActivityNewEventBinding;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class NewEventActivity extends AppCompatActivity {

    ActivityNewEventBinding binding;
    EditText eventNameEditText;
    CheckBox wholeDayCheckBox, notificationCheckBox;
    TextView timeTextView;
    MaterialButton addEventButton;
    CalendarDay calendarDay;
    CalendarEntryRepository calendarEntryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityNewEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getIntentContent();

        calendarEntryRepository = new CalendarEntryRepository(this);

        eventNameEditText = binding.eventName;
        wholeDayCheckBox = binding.wholeDayCheckbox;
        notificationCheckBox = binding.notificationCheckbox;
        timeTextView = binding.timeTextView;
        addEventButton = binding.addEventButton;

        Toolbar toolbar = binding.toolbarIncl.toolbar;;
        toolbar.setTitle("Add Event");
        setSupportActionBar(toolbar);

        wholeDayCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                timeTextView.setVisibility(View.GONE);
            } else {
                timeTextView.setVisibility(View.VISIBLE);
            }
        });

        timeTextView.setOnClickListener(v -> {
            LocalTime now = LocalTime.now();
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view, hourOfDay, minute) -> {
                        String formattedTime = String.format("%02d:%02d", hourOfDay, minute);
                        timeTextView.setText(formattedTime);
                    },
                    now.getHour(), now.getMinute(), true);
            timePickerDialog.show();
        });

        addEventButton.setOnClickListener(view -> {
            String eventName = eventNameEditText.getText().toString().trim();
            String timeText = timeTextView.getText().toString().trim();
            boolean isWholeDay = wholeDayCheckBox.isChecked();
            boolean shouldNotify = notificationCheckBox.isChecked();

            if (eventName.isEmpty()) {
                Toast.makeText(NewEventActivity.this, "All fields must be filled",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isWholeDay && timeText.equals("Select Time")) {
                Toast.makeText(NewEventActivity.this, "All fields must be filled",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            CalendarEntry calendarEntry = new CalendarEntry();
            calendarEntry.title = eventName;
            calendarEntry.localDate = calendarDay.getDate();

            if (!isWholeDay) {
                LocalTime localTime = LocalTime.parse(timeText);
                calendarEntry.localDateTime = calendarDay.getDate().atTime(localTime);
            }

            calendarEntryRepository.insertCalendarEntry(calendarEntry);

            finish();
        });
    }

    private void getIntentContent() {
        calendarDay = (CalendarDay) getIntent().getSerializableExtra(IntentGroup.DAY);
    }
}