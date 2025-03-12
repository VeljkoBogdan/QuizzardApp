package com.veljkobogdan.quizzardapp.ui.exam;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Question;
import com.veljkobogdan.quizzardapp.data.models.ExamWithQuestions;
import com.veljkobogdan.quizzardapp.databinding.ActivityLearnExamBinding;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

import java.util.ArrayList;
import java.util.List;

public class LearnExamActivity extends AppCompatActivity {
    private ActivityLearnExamBinding binding;
    private ExamWithQuestions exam;
    private List<String> allAnswers;
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

        questionLayout = binding.questionLayout;

        allAnswers = new ArrayList<>();
        getExamFromIntent();
        if (exam == null) {
            Log.e("ERROR", "Exam is not initialized in LearnExamActivity");
            finish();
            return;
        }

        Toolbar toolbar = binding.layoutIncl.toolbar;
        toolbar.setTitle(exam.exam.getTitle());
        setSupportActionBar(toolbar);

        for (Question question : exam.questionList) {
            allAnswers.add(question.getAnswer());
        }

        for (Question question : exam.questionList) {
            QuestionAnswerItem questionAnswerItem = new QuestionAnswerItem(this,
                    question, allAnswers, questionLayout);
            binding.questionLayout.addView(questionAnswerItem.getView());
        }

        // TODO: create evaluation and result calculation
    }

    private void getExamFromIntent() {
        exam = (ExamWithQuestions) getIntent()
                .getSerializableExtra(IntentGroup.EXAM_WITH_QUESTIONS);
    }
}
