package com.veljkobogdan.quizzardapp.ui.notes;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.MenuProvider;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.AppDatabase;
import com.veljkobogdan.quizzardapp.data.database.entities.Note;
import com.veljkobogdan.quizzardapp.data.database.entities.Tag;
import com.veljkobogdan.quizzardapp.data.repository.NoteRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityNewNoteBinding;
import com.veljkobogdan.quizzardapp.ui.tags.TagSelectionOverlay;
import com.veljkobogdan.quizzardapp.util.OverlayHelper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NewNoteActivity extends AppCompatActivity {
    ActivityNewNoteBinding binding;
    NoteRepository noteRepository;
    List<Tag> tags = new ArrayList<>();

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
        toolbar.setTitle("New Note"); // TODO: add a title in the action bar
        setSupportActionBar(toolbar);

        binding.addButton.setOnClickListener(view -> {
            String title = binding.title.getText().toString().trim();
            String content = binding.content.getText().toString().trim();

            if (title.isEmpty() || content.isEmpty()) return;

            Note note = new Note();
            note.setContent(content);
            note.setTitle(title);
            note.setCreatedAt(LocalDateTime.now().toString());

            new NoteRepository(this).insertNoteWithTags(note, tags);

            finish();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.tags) {
            TagSelectionOverlay tagSelectionOverlay = new TagSelectionOverlay(this);
            tagSelectionOverlay.setOnSaveListener(selectedTags -> {
                tags = selectedTags;
            });
            tagSelectionOverlay.create(null);
        }

        return super.onOptionsItemSelected(item);
    }
}