package com.veljkobogdan.quizzardapp.ui.exam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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
import com.veljkobogdan.quizzardapp.data.repository.QuestionRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityViewExamBinding;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

import java.util.List;

public class ViewExamActivity extends AppCompatActivity {
    private ActivityViewExamBinding binding;
    private ExamWithQuestions exam;
    private LinearLayout linearLayout;
    private Button questionsButton, takeExamButton, addQuestionButton;
    private QuestionRepository questionRepository;
    private ExamRepository examRepository;

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

        exam = (ExamWithQuestions) getIntent().getSerializableExtra(IntentGroup.EXAM_WITH_QUESTIONS);
        if (exam == null) {
            Log.e("ERROR", "Exam cannot be null");
            finish();
        }

        Toolbar toolbar = binding.toolbarIncl.toolbar;
        toolbar.setTitle(exam.exam.getTitle());
        setSupportActionBar(toolbar);

        linearLayout = binding.questionLayout;
        questionsButton = binding.questionsButton;
        takeExamButton = binding.takeExamButton;
        addQuestionButton = binding.addQuestionButton;

        examRepository = new ExamRepository(this);
        questionRepository = new QuestionRepository(this);

        takeExamButton.setOnClickListener(v -> {
            if (exam.questionList.size() < 2) {
                Toast.makeText(ViewExamActivity.this, "Less than 2 questions in exam",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(ViewExamActivity.this, LearnExamActivity.class);
            intent.putExtra(IntentGroup.EXAM_WITH_QUESTIONS, exam);
            startActivity(intent);
        });

        questionsButton.setOnClickListener(v -> {
            Intent intent = new Intent(ViewExamActivity.this, ViewQuestionsActivity.class);
            intent.putExtra(IntentGroup.EXAM_WITH_QUESTIONS, exam);
            startActivity(intent);
        });

        addQuestionButton.setOnClickListener(view -> {
            Intent intent = new Intent(ViewExamActivity.this, AddQuestionActivity.class);
            intent.putExtra(IntentGroup.EXAM_WITH_QUESTIONS, exam);
            startActivity(intent);
        });

        loadQuestions(exam.exam.examId);
    }

    private void loadQuestions(long examId) {
        examRepository.getExamWithQuestions(examId).observe(this, updatedExam -> {
            if (updatedExam != null) {
                exam = updatedExam;
                setupQuestionRecycler(updatedExam.questionList);
            }
        });
    }

    private void setupQuestionRecycler(List<Question> questionList) {
        linearLayout.removeAllViews();

        if (questionList.isEmpty()) {
            TextView emptyMessage = new TextView(this);
            emptyMessage.setText("No questions in this exam yet.");
            emptyMessage.setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_BodyLarge);
            emptyMessage.setPadding(32, 32, 32, 32);
            linearLayout.addView(emptyMessage);
            return;
        }

        for (Question question : questionList) {
            String questionText = question.getQuestion();
            String answerText = question.getAnswer();

            View item = LayoutInflater.from(this)
                    .inflate(R.layout.item_question, linearLayout, false);

            TextView questionView = item.findViewById(R.id.questionQuestion);
            TextView answerView = item.findViewById(R.id.questionAnswer);

            questionView.setText(questionText);
            answerView.setText(answerText);

            item.setOnLongClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(ViewExamActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.flashcard_popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    if (menuItem.getItemId() == R.id.delete) {
                        questionRepository.delete(question);
                        return true;
                    }
                    if (menuItem.getItemId() == R.id.edit) {
                        Intent i = new Intent(ViewExamActivity.this, EditQuestionActivity.class);
                        i.putExtra(IntentGroup.QUESTION, question);
                        startActivity(i);
                        return true;
                    }
                    return false;
                });
                popupMenu.show();
                return true;
            });
            linearLayout.addView(item);
        }
    }
}