package com.veljkobogdan.quizzardapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.activity.note.NotesListActivity;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilesFragment extends Fragment {
    Button files_notes_button;

    public FilesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FilesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilesFragment newInstance(String param1, String param2) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_files, container, false);
        // Find button and attach a click listener
        files_notes_button = view.findViewById(R.id.files_notes_button);
        files_notes_button.setOnClickListener(this::onNotesButtonClick);
        return view;
    }

    private void onNotesButtonClick(View v) {
        // go to NotesListActivity
        Intent intent = new Intent(getActivity(), NotesListActivity.class);
        startActivity(intent);
    }
}