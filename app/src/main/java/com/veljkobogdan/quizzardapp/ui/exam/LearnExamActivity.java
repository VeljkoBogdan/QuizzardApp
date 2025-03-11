package com.veljkobogdan.quizzardapp.ui.exam;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.models.ExamWithQuestions;
import com.veljkobogdan.quizzardapp.databinding.ActivityLearnExamBinding;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

public class LearnExamActivity extends AppCompatActivity {
    private ActivityLearnExamBinding binding;
    private ExamWithQuestions exam;
    private LinearLayout questionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityLearnExamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getExamFromIntent();

        if (this.exam == null) {
            Log.e("ERROR", "Exam is not initialized in LearnExamActivity");
            finish();
        }

        Toolbar toolbar = binding.layoutIncl.toolbar;
        toolbar.setTitle(exam.exam.getTitle());
        setSupportActionBar(toolbar);

        questionLayout = binding.questionLayout;
        // TODO: populate the layout with question-answer cards (choose the answer style)

        // TODO: create evaluation and result calculation
    }

    private void getExamFromIntent() {
        this.exam = (ExamWithQuestions) getIntent()
                .getSerializableExtra(IntentGroup.EXAM_WITH_QUESTIONS);
    }
}