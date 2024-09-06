package com.veljkobogdan.quizzardapp.activity.note;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.database.NoteDAO;
import com.veljkobogdan.quizzardapp.database.RoomDB;
import com.veljkobogdan.quizzardapp.entity.Note;


public class EditNoteActivity extends AppCompatActivity {
    ImageView image_submit, image_back;
    EditText edit_note_title, edit_note_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_note);
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
        edit_note_text.setText(note.getTitle());
        edit_note_title.setText(note.getText());
    }

    private void initWidgets(Note note) {
        // find views
        image_back = findViewById(R.id.image_back);
        image_submit = findViewById(R.id.image_submit);
        edit_note_text = findViewById(R.id.edit_note_text);
        edit_note_title = findViewById(R.id.edit_note_title);

        // set click listeners
        image_back.setOnClickListener(v -> finish());
        image_submit.setOnClickListener((View v) -> {
            String title = edit_note_title.getText().toString();
            String text = edit_note_text.getText().toString();

            if (title.isEmpty() || text.isEmpty()) {
                Toast.makeText(EditNoteActivity.this, "All fields are required", Toast.LENGTH_LONG).show();
            } else {
                Note newNote = new Note()
                        .setText(text)
                        .setTitle(title);
                NoteDAO noteDAO = RoomDB.getInstance(EditNoteActivity.this).noteDAO();
                noteDAO.update(note.getId(), newNote.getTitle(), newNote.getText(), note.getDate(), note.isPinned);
                Intent intent = new Intent(EditNoteActivity.this, NoteActivity.class);
                intent.putExtra("note", newNote);
                EditNoteActivity.this.startActivity(intent);
            }
        });
    }
}