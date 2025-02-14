package com.veljkobogdan.quizzardapp.ui.notes;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Note;
import com.veljkobogdan.quizzardapp.data.database.entities.Tag;
import com.veljkobogdan.quizzardapp.data.models.NoteWithTags;
import com.veljkobogdan.quizzardapp.data.repository.NoteRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityNewNoteBinding;
import com.veljkobogdan.quizzardapp.ui.tags.TagSelectionOverlay;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewNoteActivity extends AppCompatActivity {
    TagSelectionOverlay tagSelectionOverlay;
    ActivityNewNoteBinding binding;
    NoteRepository noteRepository;
    List<Tag> tags = new ArrayList<>();
    NoteWithTags note;

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

        initToolbar();
        getIntentExtras();
        initSaveButton();
    }

    private void initSaveButton() {
        binding.addButton.setOnClickListener(view -> {
            String title = binding.title.getText().toString().trim();
            String content = binding.content.getText().toString().trim();

            if (title.isEmpty() || content.isEmpty()) return;

            if (this.note != null) {
                note.note.setContent(content);
                note.note.setTitle(title);
                note.note.setCreatedAt(LocalDateTime.now().toString());
                noteRepository.updateNoteWithTags(note.note, tags);
            } else {
                Note newNote = new Note();
                newNote.setTitle(title);
                newNote.setContent(content);
                noteRepository.insertNoteWithTags(newNote, tags);
            }

            finish();
        });
    }

    private void getIntentExtras() {
        try {
            Bundle extras = getIntent().getExtras();
            if (!(extras != null && extras.isEmpty())) {
                this.note = (NoteWithTags) extras.getSerializable(IntentGroup.NOTE);

                binding.title.setText(this.note.note.getTitle());
                binding.content.setText(this.note.note.getContent());
                tags = this.note.tags;
            }
        } catch (Exception e) {
            Log.i("INTENT", Objects.requireNonNull(e.getMessage()));
        }
    }

    private void initToolbar() {
        Toolbar toolbar = binding.toolbarIncl.toolbar;
        toolbar.setTitle("New Note");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.tags) {
            tagSelectionOverlay = new TagSelectionOverlay(this, tags);
            tagSelectionOverlay.setOnSaveListener(selectedTags -> {
                tags = selectedTags;
            });
            tagSelectionOverlay.create(null);
        }

        return super.onOptionsItemSelected(item);
    }
}