package com.veljkobogdan.quizzardapp.ui.exam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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
import com.veljkobogdan.quizzardapp.data.repository.QuestionRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityViewQuestionsBinding;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

import java.util.List;

public class ViewQuestionsActivity extends AppCompatActivity {

    ActivityViewQuestionsBinding binding;
    LinearLayout questionLayout;
    ExamWithQuestions exam;
    ExamRepository examRepository;
    QuestionRepository questionRepository;

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
        questionRepository = new QuestionRepository(this);

        loadQuestions(exam.exam.examId);
    }

    private void loadQuestions(long examId) {
        examRepository.getExamWithQuestions(examId).observe(this, updatedExam -> {
            if (updatedExam != null) {
                setupQuestionRecycler(updatedExam.questionList);
            }
        });
    }

    private void setupQuestionRecycler(List<Question> questionList) {
        questionLayout.removeAllViews();

        if (questionList.isEmpty()) {
            TextView emptyMessage = new TextView(this);
            emptyMessage.setText("No questions in this exam yet.");
            emptyMessage.setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_BodyLarge);
            emptyMessage.setPadding(32, 32, 32, 32);
            questionLayout.addView(emptyMessage);
            return;
        }

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
                PopupMenu popupMenu = new PopupMenu(ViewQuestionsActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.flashcard_popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    if (menuItem.getItemId() == R.id.delete) {
                        questionRepository.delete(question);
                        return true;
                    }
                    if (menuItem.getItemId() == R.id.edit) {
                        // TODO: go to edit activity
                        return true;
                    }
                    return false;
                });
                popupMenu.show();
                return true;
            });
            questionLayout.addView(item);
        }
    }

    private void getIntentContent() {
        this.exam = (ExamWithQuestions) getIntent().getSerializableExtra(IntentGroup.EXAM_WITH_QUESTIONS);
    }
}