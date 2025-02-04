package com.veljkobogdan.quizzardapp.ui.main.files;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.models.NoteWithTags;
import com.veljkobogdan.quizzardapp.data.repository.NoteRepository;
import com.veljkobogdan.quizzardapp.ui.notes.NoteAdapter;

public class FilesNotesFragment extends Fragment {
    private NoteRepository noteRepository;
    private NoteAdapter noteAdapter;
    private RecyclerView recyclerView;

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

        recyclerView = requireView().findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));

        noteRepository = new NoteRepository(requireContext());
        noteAdapter = new NoteAdapter(new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(NoteWithTags note) {

            }

            @Override
            public void onNoteLongClick(NoteWithTags note, View noteView) {

            }
        });

        recyclerView.setAdapter(noteAdapter);

        loadNotes();
    }

    private void loadNotes() {
        noteRepository.getAllNotesWithTags().observe(requireActivity(), notes -> {
            if (!notes.isEmpty()) {
                noteAdapter.setNotes(notes);
                this.requireView().findViewById(R.id.noNotesText).setVisibility(View.GONE);
            } else {
                this.requireView().findViewById(R.id.noNotesText).setVisibility(View.VISIBLE);
            }
        });
    }
}