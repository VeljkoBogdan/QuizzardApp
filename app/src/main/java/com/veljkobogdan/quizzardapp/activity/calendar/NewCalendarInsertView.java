package com.veljkobogdan.quizzardapp.activity.calendar;


import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.database.CalendarDAO;
import com.veljkobogdan.quizzardapp.database.RoomDB;
import com.veljkobogdan.quizzardapp.entity.CalendarInsert;
import com.veljkobogdan.quizzardapp.helper.DateTimeFormatterHelper;
import com.veljkobogdan.quizzardapp.helper.RedirectHelper;

import java.util.Objects;

public class NewCalendarInsertView extends AppCompatActivity {
    ImageButton image_add, image_back;
    TextView title;
    EditText edit_calendar_insert_title, edit_calendar_insert_description;
    TimePicker time_picker;
    CheckBox checkbox_all_day;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_calendar_insert_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initWidgets();

        try {
            // parse date from intent
            date = String.format(Objects.requireNonNull(getIntent().getStringExtra("date")),
                    DateTimeFormatterHelper.getCalendarFormat());
        } catch (Exception e) {
            Toast.makeText(this, "Activity initialized without a date",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void initWidgets() {
        image_back = findViewById(R.id.image_back);
        image_add = findViewById(R.id.image_add);
        edit_calendar_insert_description = findViewById(R.id.edit_calendar_insert_description);
        edit_calendar_insert_title = findViewById(R.id.edit_calendar_insert_title);
        title = findViewById(R.id.title);
        time_picker = findViewById(R.id.time_picker);
        checkbox_all_day = findViewById(R.id.checkbox_all_day);

        title.setText(R.string.add_new_event);

        image_back.setOnClickListener(v -> finish());
        image_add.setOnClickListener(this::onClick);
    }

    private void onClick(View v) {
        String title = edit_calendar_insert_title.getText().toString();
        String text = edit_calendar_insert_description.getText().toString();

        if (title.isEmpty() || text.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_LONG).show();
            return;
        }

        CalendarInsert calendarInsert = new CalendarInsert()
                .setTitle(title)
                .setDescription(text)
                .setDate(date);

        if (checkbox_all_day.isChecked()) {
            calendarInsert.setTime(null);
        } else {
            calendarInsert.setTime(time_picker.getHour() + ":" + time_picker.getMinute());
        }

        CalendarDAO calendarDAO = RoomDB.getInstance(this).calendarDAO();
        calendarDAO.insert(calendarInsert);

        RedirectHelper.toMainActivity(this, 0);
    }
}