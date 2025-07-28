package com.veljkobogdan.quizzardapp.ui.schedule;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Subject;
import com.veljkobogdan.quizzardapp.data.repository.ScheduleRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityViewSubjectBinding;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

import java.time.format.TextStyle;
import java.util.Locale;

public class ViewSubjectActivity extends AppCompatActivity {

    ActivityViewSubjectBinding binding;
    Subject subject;
    ScheduleRepository scheduleRepository;
    TextView locationTextView, teacherTextView, dayTextView, timeTextView;
    Button editButton;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityViewSubjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getIntentContent();

        scheduleRepository = new ScheduleRepository(this);

        toolbar = binding.toolbarIncl.toolbar;
        toolbar.setTitle(subject.name);
        setSupportActionBar(toolbar);

        locationTextView = binding.locationText;
        teacherTextView = binding.teacherNameText;
        dayTextView = binding.dayText;
        timeTextView = binding.timeText;
        editButton = binding.editButton;

        setInfo(subject);

        editButton.setOnClickListener(view -> {
            Intent i = new Intent(ViewSubjectActivity.this, EditSubjectActivity.class);
            i.putExtra(IntentGroup.SUBJECT, subject);
            startActivity(i);
        });

        loadSubject();
    }

    @SuppressLint("SetTextI18n")
    private void setInfo(Subject subject) {
        toolbar.setTitle(subject.name);
        if (subject.location.isEmpty()) locationTextView.setVisibility(View.GONE);
        else locationTextView.setText("Location: " + subject.location);
        if (subject.teacherName.isEmpty()) teacherTextView.setVisibility(View.GONE);
        else teacherTextView.setText("Teacher: " + subject.teacherName);

        dayTextView.setText("Day: " + subject.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()));
        timeTextView.setText("Time: " + subject.startTime + " - " + subject.endTime);
    }

    private void getIntentContent() {
        subject = (Subject) getIntent().getSerializableExtra(IntentGroup.SUBJECT);
    }

    private void loadSubject() {
        scheduleRepository.getSubject(subject.subjectId).observe(ViewSubjectActivity.this,
                updatedSubject -> {
                    subject = updatedSubject;
                    setInfo(subject);
                });
    }
}