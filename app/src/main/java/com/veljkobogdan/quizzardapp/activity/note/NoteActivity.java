package com.veljkobogdan.quizzardapp.activity.note;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.entity.Note;
import com.veljkobogdan.quizzardapp.helper.RedirectHelper;

public class NoteActivity extends AppCompatActivity {
    ImageButton image_back, image_edit;
    TextView note_view_title, note_view_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.note_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // get Note from intent
        Note note = (Note) getIntent().getSerializableExtra("note");
        assert note != null;

        initWidgets(note);

        // set note data
        note_view_title.setText(note.getTitle());
        note_view_text.setText(note.getText());
    }

    private void initWidgets(Note note) {
        // find views
        image_back = findViewById(R.id.image_back);
        image_edit = findViewById(R.id.image_edit);
        note_view_title = findViewById(R.id.note_view_title);
        note_view_text = findViewById(R.id.note_view_text);

        // set click listeners
        image_back.setOnClickListener(v -> {
            RedirectHelper.toNotesListActivity(this);
        });
        image_edit.setOnClickListener(v -> {
            Intent intent = new Intent(NoteActivity.this, EditNoteActivity.class);
            intent.putExtra("note", note);
            startActivity(intent);
        });
    }
}