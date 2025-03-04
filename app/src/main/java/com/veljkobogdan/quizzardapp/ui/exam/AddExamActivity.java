package com.veljkobogdan.quizzardapp.ui.exam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Exam;
import com.veljkobogdan.quizzardapp.data.database.entities.Question;
import com.veljkobogdan.quizzardapp.data.models.ExamWithQuestions;
import com.veljkobogdan.quizzardapp.data.repository.ExamRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityAddExamBinding;

import java.util.ArrayList;
import java.util.List;

public class AddExamActivity extends AppCompatActivity {
    private ActivityAddExamBinding binding;
    private ExamRepository examRepository;
    private Button saveButton, addQuestionButton;
    private LinearLayout questionLayout;
    private EditText title;
    private final List<Question> questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityAddExamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = binding.toolbarIncl.toolbar;
        toolbar.setTitle("New Exam");
        setSupportActionBar(toolbar);

        examRepository = new ExamRepository(this);
        questionLayout = binding.questionLayout;
        saveButton = binding.addButton;
        addQuestionButton = binding.newQuestionButton;
        title = binding.title;

        addQuestionButton.setOnClickListener(view -> addQuestion());
        saveButton.setOnClickListener(view -> saveExam());

        addQuestion();
        addQuestion();
    }

    private void addQuestion() {
        View item = LayoutInflater.from(this)
                .inflate(R.layout.item_question_edit, questionLayout, false);
        View deleteButton = item.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(v -> {
            questionLayout.removeView(item);
        });

        questionLayout.addView(item);
    }

    private void saveExam() {
        String titleText = title.getText().toString().trim();

        if (titleText.isEmpty()) {
            title.setError("Title must not be empty!");
            return;
        }

        questions.clear();
        for (int i = 0; i < questionLayout.getChildCount(); i++) {
            View question = questionLayout.getChildAt(i);
            EditText questionQuestion = question.findViewById(R.id.questionQuestion);
            EditText questionAnswer = question.findViewById(R.id.questionAnswer);

            String questionText = questionQuestion.getText().toString().trim();
            String answerText = questionAnswer.getText().toString().trim();

            if (questionText.isEmpty()) {
                questionQuestion.setError("Cannot be empty!");
                return;
            }

            if (answerText.isEmpty()) {
                questionAnswer.setError("Cannot be empty!");
                return;
            }

            questions.add(new Question(questionText, answerText));
        }

        if (questions.size() < 2) {
            Toast.makeText(this, "Must have more than 2 questions", Toast.LENGTH_LONG)
                    .show();
            return;
        }

        Exam exam = new Exam(titleText);
        examRepository.insertExamWithQuestions(exam, questions);

        finish();
    }
}