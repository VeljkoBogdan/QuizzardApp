package com.veljkobogdan.quizzardapp.ui.schedule;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Schedule;
import com.veljkobogdan.quizzardapp.data.repository.ScheduleRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityAddScheduleBinding;

public class AddScheduleActivity extends AppCompatActivity {

    ActivityAddScheduleBinding binding;
    EditText scheduleNameEditText;
    Button addScheduleButton;
    ScheduleRepository scheduleRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityAddScheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        scheduleNameEditText = binding.scheduleName;
        addScheduleButton = binding.addScheduleButton;
        scheduleRepository = new ScheduleRepository(this);

        addScheduleButton.setOnClickListener(view -> {
            String scheduleName;
            scheduleName = scheduleNameEditText.getText().toString().trim();

            if (scheduleName.isEmpty()) {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            Schedule schedule = new Schedule();
            schedule.name = scheduleName;

            scheduleRepository.insertScheduleEntry(schedule);
        });
    }
}