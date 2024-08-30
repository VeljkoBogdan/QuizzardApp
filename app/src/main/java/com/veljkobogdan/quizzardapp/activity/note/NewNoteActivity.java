package com.veljkobogdan.quizzardapp.activity.note;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.veljkobogdan.quizzardapp.helper.RedirectHelper;

public class NewNoteActivity extends AppCompatActivity {
    ImageButton image_back, image_add;
    EditText edit_note_title, edit_note_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.new_note_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        image_back = findViewById(R.id.image_back);
        image_add = findViewById(R.id.image_add);
        edit_note_title = findViewById(R.id.edit_note_title);
        edit_note_text = findViewById(R.id.edit_note_text);

        image_back.setOnClickListener(v -> RedirectHelper.toNotesListActivity(this));
        image_add.setOnClickListener(this::onClick);
    }

    private void onClick(View v) {
        String title = edit_note_title.getText().toString();
        String text = edit_note_text.getText().toString();

        if (title.isEmpty() || text.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_LONG).show();
            return;
        }

        Note note = new Note()
                .setText(text)
                .setTitle(title)
                .setDate(android.text.format.DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString());

        NoteDAO noteDAO = RoomDB.getInstance(this).noteDAO();
        noteDAO.insert(note);

        RedirectHelper.toNotesListActivity(this);
    }
}