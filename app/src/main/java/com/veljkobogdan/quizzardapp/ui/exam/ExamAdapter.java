package com.veljkobogdan.quizzardapp.ui.exam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.models.ExamWithQuestions;
import com.veljkobogdan.quizzardapp.data.repository.ExamRepository;

import java.util.ArrayList;
import java.util.List;

/** @noinspection ClassEscapesDefinedScope*/
public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExamViewHolder> {
    private final Context context;
    private final List<ExamWithQuestions> exams = new ArrayList<>();
    protected OnExamClickListener onExamClickListener;
    private ExamRepository examRepository;

    public ExamAdapter(Context context, OnExamClickListener onExamClickListener) {
        this.context = context;
        this.onExamClickListener = onExamClickListener;
        this.examRepository = new ExamRepository(context);
    }

    @NonNull
    @Override
    public ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exam, parent, false);

        return new ExamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamViewHolder holder, int position) {
        holder.bind(exams.get(position));
    }

    @Override
    public int getItemCount() {
        return exams.size();
    }

    public void updateExams(List<ExamWithQuestions> newExams) {
        DiffUtil.Callback callback = new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return exams.size();
            }

            @Override
            public int getNewListSize() {
                return newExams.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return exams.get(oldItemPosition) == newExams.get(newItemPosition);
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return exams.get(oldItemPosition).equals(newExams.get(newItemPosition));
            }
        };

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        exams.clear();
        exams.addAll(newExams);
        result.dispatchUpdatesTo(this);
    }

    class ExamViewHolder extends RecyclerView.ViewHolder {
        TextView examTitle, examContent;

        public ExamViewHolder(@NonNull View itemView) {
            super(itemView);
            examTitle = itemView.findViewById(R.id.examTitle);
            examContent = itemView.findViewById(R.id.examContent);

            // Handle item clicks
            itemView.setOnClickListener(view -> {
                if (onExamClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onExamClickListener.onClick(exams.get(position));
                    }
                }
            });

            // Handle long press
            itemView.setOnLongClickListener(view -> {
                return true;
            });
        }

        public void bind(ExamWithQuestions exam) {
            examTitle.setText(exam.exam.getTitle());
            String examContentText = "Questions: " + exam.questionList.size();
            examContent.setText(examContentText);
        }
    }

    public interface OnExamClickListener {
        void onClick(ExamWithQuestions examWithQuestions);
    }
}
