package com.veljkobogdan.quizzardapp.ui.main.files;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.repository.NoteRepository;
import com.veljkobogdan.quizzardapp.ui.notes.NewNoteActivity;
import com.veljkobogdan.quizzardapp.ui.notes.NoteAdapter;
import com.veljkobogdan.quizzardapp.util.INoteLoader;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

public class FilesNotesFragment extends Fragment implements INoteLoader {
    private NoteRepository noteRepository;
    private NoteAdapter noteAdapter;
    private RecyclerView recyclerView;
    private TextView noNotesText;

    public FilesNotesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_files_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noNotesText = requireView().findViewById(R.id.noNotesText);

        recyclerView = requireView().findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));

        noteRepository = new NoteRepository(requireContext());
        noteAdapter = new NoteAdapter(requireContext(), note -> {
            try {
                Intent intent = new Intent(requireContext(), NewNoteActivity.class);
                intent.putExtra(IntentGroup.NOTE, note);
                startActivity(intent);
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }
        });

        recyclerView.setAdapter(noteAdapter);

        loadNotes();
    }

    @Override
    public void loadNotes() {
        noteRepository.getAllNotesWithTags().observe(requireActivity(), notes -> {
            if (!notes.isEmpty()) {
                noteAdapter.updateNotes(notes);
                noNotesText.setVisibility(View.GONE);
            } else {
                noNotesText.setVisibility(View.VISIBLE);
            }
        });
    }
}