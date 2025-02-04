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

        noteAdapter = new NoteAdapter(new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(NoteWithTags note) {
                try {
                    Intent intent = new Intent(NotesActivity.this, NewNoteActivity.class);
                    intent.putExtra(NewNoteActivity.NOTE, note);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                }
            }

            @Override
            public void onNoteLongClick(NoteWithTags note, View view) {
                PopupMenu menu = new PopupMenu(NotesActivity.this, view);

                menu.getMenuInflater().inflate(R.menu.note_popup_menu, menu.getMenu());
                menu.setOnMenuItemClickListener(menuItem -> {
                    if (menuItem.getItemId() == R.id.delete) {
                        Toast.makeText(NotesActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                });

                menu.show();
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

    private void loadNotes() {
        noteRepository.getAllNotesWithTags().observe(this, notes -> {
            if (!notes.isEmpty()) {
                noteAdapter.setNotes(notes);
                binding.noNotesText.setVisibility(View.GONE);
            } else {
                binding.noNotesText.setVisibility(View.VISIBLE);
            }
        });
    }
}