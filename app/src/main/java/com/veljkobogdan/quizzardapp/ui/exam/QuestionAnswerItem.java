package com.veljkobogdan.quizzardapp.ui.exam;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Question;
import com.veljkobogdan.quizzardapp.util.ListUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionAnswerItem {
    private final View view;
    private final TextView questionTitle;
    private final FlexboxLayout answersFlexbox;
    private final List<String> allAnswers;
    private final int numOfItems;
    private final Random random = new Random();
    private String correctAnswer;
    private final List<SelectableAnswer> selectableAnswers;

    public QuestionAnswerItem(Context context, Question question, List<String> allAnswers, ViewGroup root) {
        this.view = LayoutInflater.from(context)
                .inflate(R.layout.item_question_answer_card, root, false);
        this.questionTitle = view.findViewById(R.id.questionTitle);
        this.answersFlexbox = view.findViewById(R.id.answersFlexbox);
        this.allAnswers = allAnswers;
        this.numOfItems = Math.min(allAnswers.size(), 4);
        selectableAnswers = new ArrayList<>();

        setupQuestion(question);
    }

    public String getCorrectAnswer() {
        return this.correctAnswer;
    }

    public List<SelectableAnswer> getSelectableAnswers() {
        return selectableAnswers;
    }

    private void setupQuestion(Question question) {
        questionTitle.setText(question.getQuestion());
        correctAnswer = question.getAnswer();

        int correctAnswerPosition = random.nextInt(numOfItems);
        List<String> usedAnswers = new ArrayList<>();
        usedAnswers.add(correctAnswer);

        for (int i = 0; i < numOfItems; i++) {
            SelectableAnswer selectableAnswer = new SelectableAnswer(view.getContext(), answersFlexbox);

            if (i == correctAnswerPosition) {
                selectableAnswer.setAnswerText(correctAnswer);
            } else {
                String randomAnswer;
                do {
                    randomAnswer = ListUtil.getRandomListItem(allAnswers);
                } while (usedAnswers.contains(randomAnswer));

                selectableAnswer.setAnswerText(randomAnswer);
                usedAnswers.add(randomAnswer);
            }

            selectableAnswer.setOnClickListener(v -> {
                for (SelectableAnswer sa : selectableAnswers) {
                    sa.setSelected(false);
                }
                selectableAnswer.setSelected(true);
            });


            selectableAnswers.add(selectableAnswer);
            answersFlexbox.addView(selectableAnswer.getView());
        }
    }

    public View getView() {
        return view;
    }
}
