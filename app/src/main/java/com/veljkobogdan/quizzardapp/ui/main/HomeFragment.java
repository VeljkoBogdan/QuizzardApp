package com.veljkobogdan.quizzardapp.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.service.StreakService;
import com.veljkobogdan.quizzardapp.ui.exam.ExamsActivity;
import com.veljkobogdan.quizzardapp.ui.sets.FlashcardSetsActivity;
import com.veljkobogdan.quizzardapp.ui.notes.NotesActivity;

public class HomeFragment extends Fragment {
    private StreakService streakService;
    private TextView streakText;

    public HomeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        streakService = new StreakService(requireContext());
        streakService.updateStreak();
        int streak = streakService.getCounterOfConsecutiveDays();

        streakText = view.findViewById(R.id.streak);
        streakText.setText(Integer.toString(streak));

        // TEMP
        view.findViewById(R.id.buttonNotes).setOnClickListener(item -> {
            try {
                Intent intent = new Intent(requireContext(), NotesActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.buttonFlashcardSets).setOnClickListener(item -> {
            try {
                Intent intent = new Intent(requireContext(), FlashcardSetsActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.buttonExams).setOnClickListener(item -> {
            try {
                Intent intent = new Intent(requireContext(), ExamsActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }
        });
    }
}