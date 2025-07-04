package com.veljkobogdan.quizzardapp.ui.exam;

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
import com.veljkobogdan.quizzardapp.data.database.entities.Flashcard;
import com.veljkobogdan.quizzardapp.data.database.entities.Question;
import com.veljkobogdan.quizzardapp.data.models.ExamWithQuestions;
import com.veljkobogdan.quizzardapp.data.repository.ExamRepository;
import com.veljkobogdan.quizzardapp.data.repository.QuestionRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityAddFlashcardBinding;
import com.veljkobogdan.quizzardapp.databinding.ActivityAddQuestionBinding;
import com.veljkobogdan.quizzardapp.ui.flashcards.AddFlashcardActivity;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

public class AddQuestionActivity extends AppCompatActivity {

    ActivityAddQuestionBinding binding;
    ExamWithQuestions exam;
    EditText questionEditText, answerEditText;
    Button saveButton;
    ExamRepository examRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityAddQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = binding.layoutIncl.toolbar;
        toolbar.setTitle("Add an Exam");
        setSupportActionBar(toolbar);

        getIntentContent();

        questionEditText = binding.questionQuestion;
        answerEditText = binding.questionAnswer;
        saveButton = binding.addButton;

        examRepository = new ExamRepository(this);

        saveButton.setOnClickListener(view -> {
            String questionText, answerText;
            questionText = questionEditText.getText().toString().trim();
            answerText = answerEditText.getText().toString().trim();

            if (questionText.isEmpty() || answerText.isEmpty()) {
                Toast.makeText(AddQuestionActivity.this, "All fields must be filled",
                        Toast.LENGTH_SHORT).show();
            }

            Question question = new Question();
            question.setQuestion(questionText);
            question.setAnswer(answerText);

            examRepository.addQuestionToExam(question, exam.exam.examId);

            Toast.makeText(AddQuestionActivity.this, "Question has been added",
                    Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void getIntentContent() {
        exam = (ExamWithQuestions) getIntent().getSerializableExtra(IntentGroup.EXAM_WITH_QUESTIONS);
    }
}