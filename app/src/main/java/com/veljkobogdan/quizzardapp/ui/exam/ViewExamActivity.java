package com.veljkobogdan.quizzardapp.ui.exam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Question;
import com.veljkobogdan.quizzardapp.data.models.ExamWithQuestions;
import com.veljkobogdan.quizzardapp.databinding.ActivityViewExamBinding;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

public class ViewExamActivity extends AppCompatActivity {
    private ActivityViewExamBinding binding;
    private ExamWithQuestions exam;
    private LinearLayout linearLayout;
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

        linearLayout = binding.questionLayout;
        title = binding.examTitle;
        questionsButton = binding.questionsButton;
        takeExamButton = binding.takeExamButton;

        title.setText(exam.exam.getTitle());

        takeExamButton.setOnClickListener(v -> {
            Intent intent = new Intent(ViewExamActivity.this, LearnExamActivity.class);
            intent.putExtra(IntentGroup.EXAM_WITH_QUESTIONS, exam);
            startActivity(intent);
        });

        questionsButton.setOnClickListener(v -> {
            Intent intent = new Intent(ViewExamActivity.this, ViewQuestionsActivity.class);
            intent.putExtra(IntentGroup.EXAM_WITH_QUESTIONS, exam);
            startActivity(intent);
        });

        setupQuestionRecycler();
    }

    private void setupQuestionRecycler() {
        for (Question question : exam.questionList) {
            String questionText = question.getQuestion();
            String answerText = question.getAnswer();

            View item = LayoutInflater.from(this)
                    .inflate(R.layout.item_question, linearLayout, false);

            TextView questionView = item.findViewById(R.id.questionQuestion);
            TextView answerView = item.findViewById(R.id.questionAnswer);

            questionView.setText(questionText);
            answerView.setText(answerText);

            item.setOnClickListener(view -> {
                // TODO: add a click listener to questions
            });
            linearLayout.addView(item);
        }
    }
}