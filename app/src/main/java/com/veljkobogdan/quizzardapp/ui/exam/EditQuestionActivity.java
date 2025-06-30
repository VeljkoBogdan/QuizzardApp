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
import com.veljkobogdan.quizzardapp.data.database.entities.Question;
import com.veljkobogdan.quizzardapp.data.repository.QuestionRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityEditQuestionBinding;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

public class EditQuestionActivity extends AppCompatActivity {

    ActivityEditQuestionBinding binding;
    EditText questionEditText, answerEditText;
    Question question;
    QuestionRepository questionRepository;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityEditQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = binding.toolbarIncl.toolbar;
        toolbar.setTitle("Editing Question");
        setSupportActionBar(toolbar);

        getIntentContent();

        questionEditText = binding.questionQuestion;
        answerEditText = binding.questionAnswer;
        saveButton = binding.addButton;

        questionRepository = new QuestionRepository(this);

        questionEditText.setText(question.getQuestion());
        answerEditText.setText(question.getAnswer());
        saveButton.setOnClickListener(view -> {
            String questionText, answerText;
            questionText = questionEditText.getText().toString().trim();
            answerText = answerEditText.getText().toString().trim();

            if (questionText.isEmpty() || answerText.isEmpty()) {
                Toast.makeText(EditQuestionActivity.this, "All fields must be filled",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            question.setQuestion(questionText);
            question.setAnswer(answerText);
            questionRepository.update(question);
            finish();
        });
    }

    private void getIntentContent() {
        question = (Question) getIntent().getSerializableExtra(IntentGroup.QUESTION);
    }
}