package com.veljkobogdan.quizzardapp.ui.exam;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.models.ExamWithQuestions;
import com.veljkobogdan.quizzardapp.databinding.ActivityViewExamBinding;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

public class ViewExamActivity extends AppCompatActivity {
    private ActivityViewExamBinding binding;
    private ExamWithQuestions exam;
    private RecyclerView recyclerView;
    private TextView title;
    private Button questionsButton, takeExamButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityViewExamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = binding.toolbarIncl.toolbar;
        toolbar.setTitle("Exam");
        setSupportActionBar(toolbar);

        exam = (ExamWithQuestions) getIntent().getSerializableExtra(IntentGroup.EXAM_WITH_QUESTIONS);
        if (exam == null) {
            Log.e("ERROR", "Exam cannot be null");
            finish();
        }

        recyclerView = binding.recycler;
        title = binding.examTitle;
        questionsButton = binding.questionsButton;
        takeExamButton = binding.takeExamButton;

        title.setText(exam.exam.getTitle());
    }
}