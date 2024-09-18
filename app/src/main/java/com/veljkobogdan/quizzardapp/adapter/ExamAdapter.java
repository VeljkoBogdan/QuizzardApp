package com.veljkobogdan.quizzardapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.entity.Exam;

import java.util.List;

public class ExamAdapter extends ArrayAdapter<Exam> {
    public ExamAdapter(Context context, List<Exam> exams) {
        super(context, 0, exams);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Exam exam = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.exam_list_item, parent, false);
        }

        TextView examNameTextView = convertView.findViewById(R.id.exam_name_text_view);
        examNameTextView.setText(exam.getName());

        return convertView;
    }
}
