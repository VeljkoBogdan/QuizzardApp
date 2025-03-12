package com.veljkobogdan.quizzardapp.ui.exam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.veljkobogdan.quizzardapp.R;

public class SelectableAnswer {
    private final View view;
    private final TextView answerTitle;

    public SelectableAnswer(Context context, ViewGroup root) {
        this.view = LayoutInflater.from(context)
                .inflate(R.layout.item_answer_checkbox, root, false);
        this.answerTitle = view.findViewById(R.id.answerTitle);
    }

    public void setAnswerText(String text) {
        answerTitle.setText(text);
    }

    public View getView() {
        return view;
    }

    public void setSelected(boolean selected) {
        view.setSelected(selected);
        MaterialCardView cardView = view.findViewById(R.id.answerCard);

        cardView.setCardBackgroundColor(selected ?
            view.getResources().getColor(R.color.md_theme_surfaceVariant, view.getContext().getTheme()) :
            view.getResources().getColor(R.color.md_theme_surface, view.getContext().getTheme())
        );
    }

    public void setOnClickListener(View.OnClickListener listener) {
        view.setOnClickListener(listener);
    }
}
