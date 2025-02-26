package com.veljkobogdan.quizzardapp.ui.exam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.repository.ExamRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityExamsBinding;

public class ExamsActivity extends AppCompatActivity {
    private ActivityExamsBinding binding;
    private ExamRepository examRepository;
    private FloatingActionButton addButton;
    private RecyclerView recyclerView;
    private ExamAdapter examAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityExamsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = binding.toolbarIncl.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setTitle("Your Exams");
        examRepository = new ExamRepository(this);

        addButton = binding.addButton;
        addButton.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(this, AddExamActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }
        });

        examAdapter = new ExamAdapter(this, examWithQuestions -> {
           // TODO: Handle on Exam click
        });
        recyclerView = binding.recycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(examAdapter);

        loadExams();
    }

    public void loadExams() {
        try {
            examRepository.getExamsWithQuestions().observe(this, exams -> {
                if (!exams.isEmpty()) {
                    examAdapter.updateExams(exams);
                    binding.noExamsText.setVisibility(View.INVISIBLE);
                } else {
                    binding.noExamsText.setVisibility(View.VISIBLE);
                }
            });
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
    }
}