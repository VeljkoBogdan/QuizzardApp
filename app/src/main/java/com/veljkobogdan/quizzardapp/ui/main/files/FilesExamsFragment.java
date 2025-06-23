package com.veljkobogdan.quizzardapp.ui.main.files;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.repository.ExamRepository;
import com.veljkobogdan.quizzardapp.ui.exam.AddExamActivity;
import com.veljkobogdan.quizzardapp.ui.exam.ExamAdapter;
import com.veljkobogdan.quizzardapp.ui.exam.ViewExamActivity;
import com.veljkobogdan.quizzardapp.util.IExamLoader;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

public class FilesExamsFragment extends Fragment implements IExamLoader {
    TextView noExamsText;
    ExamRepository examRepository;
    ExamAdapter examAdapter;
    RecyclerView recyclerView;

    public FilesExamsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_files_examx, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        examRepository = new ExamRepository(requireContext());
        noExamsText = requireView().findViewById(R.id.noExamsText);

        examAdapter = new ExamAdapter(requireContext(), examWithQuestions -> {
            Intent intent = new Intent(requireContext(), ViewExamActivity.class);
            intent.putExtra(IntentGroup.EXAM_WITH_QUESTIONS, examWithQuestions);
            startActivity(intent);
        });
        recyclerView = requireView().findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(examAdapter);

        loadExams();
    }

    @Override
    public void loadExams() {
        try {
            examRepository.getExamsWithQuestions().observe(requireActivity(), exams -> {
                if (!exams.isEmpty()) {
                    examAdapter.updateExams(exams);
                    noExamsText.setVisibility(View.INVISIBLE);
                } else {
                    noExamsText.setVisibility(View.VISIBLE);
                }
            });
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
    }
}