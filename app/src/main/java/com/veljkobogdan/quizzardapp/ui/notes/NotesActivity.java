package com.veljkobogdan.quizzardapp.ui.notes;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.repository.NoteRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityNotesBinding;

public class NotesActivity extends AppCompatActivity {
    ActivityNotesBinding binding;
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    NoteRepository noteRepository;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        noteRepository = new NoteRepository(this);

        Toolbar toolbar = binding.toolbarIncl.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setTitle("Notes");

        floatingActionButton = binding.addButton;
        floatingActionButton.setOnClickListener(l -> {
            // TODO: Intent to new Note
        });

        recyclerView = binding.recycler;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));

        noteAdapter = new NoteAdapter(note -> {
            // TODO: Handle note click
        });

        recyclerView.setAdapter(noteAdapter);

        loadNotes();
    }

    private void loadNotes() {
        noteRepository.getAllNotes().observe(this, notes -> {
            if (!notes.isEmpty()) {
                noteAdapter.setNotes(notes);
                binding.noNotesText.setVisibility(View.GONE);
            } else {
                binding.noNotesText.setVisibility(View.VISIBLE);
            }
        });
    }
}