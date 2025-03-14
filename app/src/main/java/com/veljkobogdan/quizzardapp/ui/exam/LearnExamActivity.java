package com.veljkobogdan.quizzardapp.ui.exam;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.flexbox.FlexboxLayout;
import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Question;
import com.veljkobogdan.quizzardapp.data.models.ExamWithQuestions;
import com.veljkobogdan.quizzardapp.databinding.ActivityLearnExamBinding;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LearnExamActivity extends AppCompatActivity {
    private ActivityLearnExamBinding binding;
    private ExamWithQuestions exam;
    private List<String> allAnswers;
    private LinearLayout questionLayout;
    private List<QuestionAnswerItem> questionAnswerItems;
    private Button getExamResultsButton;

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
        getExamResultsButton = binding.getExamResults;

        questionAnswerItems = new ArrayList<>();
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
            questionAnswerItems.add(questionAnswerItem);
        }

        getExamResultsButton.setOnClickListener(v -> calculateResults());
    }

    private void getExamFromIntent() {
        exam = (ExamWithQuestions) getIntent()
                .getSerializableExtra(IntentGroup.EXAM_WITH_QUESTIONS);
    }

    private void calculateResults() {
        int totalQuestions = questionAnswerItems.size();
        int correctAnswers = 0;

        for (QuestionAnswerItem questionAnswerItem : questionAnswerItems) {
            for (SelectableAnswer selectableAnswer : questionAnswerItem.getSelectableAnswers()) {
                selectableAnswer.clearHighlight();
                if (selectableAnswer.isSelected()) {
                    if (Objects.equals(selectableAnswer.getAnswer(), questionAnswerItem.getCorrectAnswer())) {
                        correctAnswers++;
                        selectableAnswer.highlightCorrect();
                    } else {
                        selectableAnswer.highlightIncorrect();
                    }
                }
            }
        }

        // TODO: add an intent to a ExamResult
        double d = (double) correctAnswers / totalQuestions * 100.0;
        String formatted = String.format("%.2f", d);
        String result = formatted + "%";
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }
}
