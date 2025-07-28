package com.veljkobogdan.quizzardapp.ui.schedule;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Schedule;
import com.veljkobogdan.quizzardapp.data.repository.ScheduleRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityEditScheduleBinding;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

public class EditScheduleActivity extends AppCompatActivity {

    ActivityEditScheduleBinding binding;
    Schedule schedule;
    ScheduleRepository scheduleRepository;
    EditText editTextTitle;
    Button saveScheduleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityEditScheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = binding.toolbarIncl.toolbar;
        toolbar.setTitle("Edit Schedule");
        setSupportActionBar(toolbar);

        scheduleRepository = new ScheduleRepository(this);
        editTextTitle = binding.scheduleName;
        saveScheduleButton = binding.saveScheduleButton;

        getIntentContent();

        editTextTitle.setText(schedule.name);
        saveScheduleButton.setOnClickListener(view -> {
            String scheduleName;
            scheduleName = editTextTitle.getText().toString().trim();

            if (scheduleName.isEmpty()) {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            schedule.name = scheduleName;
            scheduleRepository.updateSchedule(schedule);
            finish();
        });
    }

    private void getIntentContent() {
        schedule = (Schedule) getIntent().getSerializableExtra(IntentGroup.SCHEDULE);
    }
}