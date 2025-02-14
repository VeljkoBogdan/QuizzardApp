package com.veljkobogdan.quizzardapp.ui.notes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.models.NoteWithTags;
import com.veljkobogdan.quizzardapp.data.repository.NoteRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityNotesBinding;
import com.veljkobogdan.quizzardapp.util.INoteLoader;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

public class NotesActivity extends AppCompatActivity implements INoteLoader {
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

        initToolbar();
        initFloatingButton();
        initNotesRecycler();
    }

    private void initToolbar() {
        Toolbar toolbar = binding.toolbarIncl.toolbar;
        toolbar.setTitle("Notes");
        setSupportActionBar(toolbar);
    }

    private void initNotesRecycler() {
        recyclerView = binding.recycler;
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));

        noteAdapter = new NoteAdapter(this, note -> {
            try {
                Intent intent = new Intent(NotesActivity.this, NewNoteActivity.class);
                intent.putExtra(IntentGroup.NOTE, note);
                startActivity(intent);
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }
        });

        recyclerView.setAdapter(noteAdapter);

        loadNotes();
    }

    private void initFloatingButton() {
        floatingActionButton = binding.addButton;
        floatingActionButton.setOnClickListener(l -> {
            try {
                Intent intent = new Intent(this, NewNoteActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }
        });
    }

    @Override
    public void loadNotes() {
        noteRepository.getAllNotesWithTags().observe(this, notes -> {
            if (!notes.isEmpty()) {
                noteAdapter.updateNotes(notes);
                binding.noNotesText.setVisibility(View.GONE);
            } else {
                binding.noNotesText.setVisibility(View.VISIBLE);
            }
        });
    }
}