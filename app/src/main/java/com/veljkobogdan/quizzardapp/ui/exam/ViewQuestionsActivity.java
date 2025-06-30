package com.veljkobogdan.quizzardapp.ui.exam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
import com.veljkobogdan.quizzardapp.data.repository.ExamRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityViewQuestionsBinding;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

import java.util.List;

public class ViewQuestionsActivity extends AppCompatActivity {

    ActivityViewQuestionsBinding binding;
    LinearLayout questionLayout;
    ExamWithQuestions exam;
    ExamRepository examRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityViewQuestionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getIntentContent();

        Toolbar toolbar = binding.toolbarIncl.toolbar;
        toolbar.setTitle(exam.exam.getTitle());
        setSupportActionBar(toolbar);

        questionLayout = binding.questionLayout;

        examRepository = new ExamRepository(this);

        loadQuestions(exam.exam.examId);
    }

    private void loadQuestions(long examId) {
        examRepository.getExamWithQuestions(examId).observe(this, updatedExam -> {
            if (updatedExam != null && !updatedExam.questionList.isEmpty()) {
                setupQuestionRecycler(updatedExam.questionList);
            }
        });
    }

    private void setupQuestionRecycler(List<Question> questionList) {
        for (Question question : questionList) {
            String questionText = question.getQuestion();
            String answerText = question.getAnswer();

            View item = LayoutInflater.from(this)
                    .inflate(R.layout.item_question, questionLayout, false);

            TextView questionView = item.findViewById(R.id.questionQuestion);
            TextView answerView = item.findViewById(R.id.questionAnswer);

            questionView.setText(questionText);
            answerView.setText(answerText);

            item.setOnLongClickListener(view -> {

                return false;
            });
            questionLayout.addView(item);
        }
    }

    private void getIntentContent() {
        this.exam = (ExamWithQuestions) getIntent().getSerializableExtra(IntentGroup.EXAM_WITH_QUESTIONS);
    }
}