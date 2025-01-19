package com.veljkobogdan.quizzardapp.ui.notes;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.AppDatabase;
import com.veljkobogdan.quizzardapp.data.database.entities.Note;
import com.veljkobogdan.quizzardapp.data.repository.NoteRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityNewNoteBinding;

import java.time.LocalDateTime;

public class NewNoteActivity extends AppCompatActivity {
    ActivityNewNoteBinding binding;
    NoteRepository noteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityNewNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        noteRepository = new NoteRepository(this);

        Toolbar toolbar = binding.toolbarIncl.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setTitle("New Note"); // TODO: add a title in the action bar

        binding.addButton.setOnClickListener(view -> {
            String title = binding.title.getText().toString().trim();
            String content = binding.content.getText().toString().trim();

            if (title.isEmpty() || content.isEmpty()) {
                return;
            }

            Note note = new Note();
            note.setContent(content);
            note.setTitle(title);
            note.setCreatedAt(LocalDateTime.now().toString());

            AppDatabase.getInstance(this).noteDao().insert(note);

            finish();
        });
    }
}