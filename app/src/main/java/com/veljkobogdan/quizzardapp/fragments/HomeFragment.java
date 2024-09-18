package com.veljkobogdan.quizzardapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.helper.RedirectHelper;

public class HomeFragment extends Fragment {
    Button create_flashcard_button, create_notes_button;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initWidgets(view);
        return view;
    }

    private void initWidgets(View view) {
        create_flashcard_button = view.findViewById(R.id.create_flashcard_button);
        create_notes_button = view.findViewById(R.id.create_notes_button);

        create_flashcard_button.setOnClickListener(v -> {
            RedirectHelper.toNewFlashcardActivity(getContext(), 0);
        });
        create_notes_button.setOnClickListener(v -> {
            RedirectHelper.toNewNoteActivity(getContext(), 0);
        });
    }
}