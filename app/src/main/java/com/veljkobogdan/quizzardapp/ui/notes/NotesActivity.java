package com.veljkobogdan.quizzardapp.ui.notes;

import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.AppDatabase;
import com.veljkobogdan.quizzardapp.data.database.entities.Note;
import com.veljkobogdan.quizzardapp.databinding.ActivityNotesBinding;

import java.util.List;

public class NotesActivity extends AppCompatActivity {
    ActivityNotesBinding binding;
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    private List<Note> notes;

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

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setTitle("Notes");

        recyclerView = binding.recycler;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));

        noteAdapter = new NoteAdapter(note -> {
            // Handle note click

        });

        recyclerView.setAdapter(noteAdapter);

        loadNotes();
    }

    private void loadNotes() {
        try {
            notes = AppDatabase.getInstance(this).noteDao().getAll();
            noteAdapter.setNotes(notes);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}