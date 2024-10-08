package com.veljkobogdan.quizzardapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.helper.RedirectHelper;

public class FilesFragment extends Fragment {
    Button files_notes_button;
    Button files_flashcards_button, files_flashcard_collections_button;

    public FilesFragment() {
        // Required empty public constructor
    }

    public static FilesFragment newInstance() {
        FilesFragment fragment = new FilesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_files, container, false);
        // Find buttons and attach a click listener
        files_notes_button = view.findViewById(R.id.files_notes_button);
        files_notes_button.setOnClickListener(this::onNotesButtonClick);
        files_flashcards_button = view.findViewById(R.id.files_flashcards_button);
        files_flashcards_button.setOnClickListener(this::onFlashcardsButtonClick);
        files_flashcard_collections_button = view.findViewById(R.id.files_flashcard_collection_button);
        files_flashcard_collections_button.setOnClickListener(this::onFlashcardsCollectionButtonClick);
        return view;
    }

    private void onFlashcardsCollectionButtonClick(View view) {
        try {
            RedirectHelper.toFlashcardCollectionListView(this.getActivity(), Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void onFlashcardsButtonClick(View view) {
        try {
            RedirectHelper.toFlashcardListView(this.getActivity(), Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void onNotesButtonClick(View v) {
        // go to NotesListActivity
        try {
            RedirectHelper.toNotesListActivity(this.getActivity(), Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}