package com.veljkobogdan.quizzardapp.activity.flashcard;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.database.FlashcardCollectionDAO;
import com.veljkobogdan.quizzardapp.database.RoomDB;
import com.veljkobogdan.quizzardapp.entity.FlashcardCollection;

public class CreateFlashcardCollectionActivity extends AppCompatActivity {
    EditText nameEditText;
    EditText descriptionEditText;
    ImageButton image_add, image_back;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flashcard_collection);

        nameEditText = findViewById(R.id.name_edit_text);
        descriptionEditText = findViewById(R.id.description_edit_text);

        image_add = findViewById(R.id.image_add);
        image_back = findViewById(R.id.image_back);

        title = findViewById(R.id.title);
        title.setText(R.string.create_flashcard_collection);

        image_add.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String description = descriptionEditText.getText().toString();

            if (!name.isEmpty()) {
                FlashcardCollection flashcardCollection = new FlashcardCollection()
                        .setName(name)
                        .setDescription(description);
                // Save the flashcard collection to the database
                FlashcardCollectionDAO flashcardCollectionDAO = RoomDB
                        .getInstance(CreateFlashcardCollectionActivity.this)
                        .flashcardCollectionDAO();
                flashcardCollectionDAO.insert(flashcardCollection);

                Toast.makeText(CreateFlashcardCollectionActivity.this,
                        "Flashcard collection created!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(CreateFlashcardCollectionActivity.this,
                        "Please fill in the name field", Toast.LENGTH_SHORT).show();
            }
        });
    }
}