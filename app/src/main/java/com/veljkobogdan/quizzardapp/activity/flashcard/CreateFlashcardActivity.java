package com.veljkobogdan.quizzardapp.activity.flashcard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.database.FlashcardDAO;
import com.veljkobogdan.quizzardapp.database.RoomDB;
import com.veljkobogdan.quizzardapp.entity.Flashcard;

public class CreateFlashcardActivity extends AppCompatActivity {
    EditText questionEditText;
    EditText answerEditText;
    ImageButton image_add, image_back;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_create_flashcard);

            questionEditText = findViewById(R.id.question_edit_text);
            answerEditText = findViewById(R.id.answer_edit_text);

            image_add = findViewById(R.id.image_add);
            image_back = findViewById(R.id.image_back);

            title = findViewById(R.id.title);
            title.setText(R.string.create_flashcard);

            image_back.setOnClickListener(v -> finish());
            image_add.setOnClickListener(v -> {
                String question = questionEditText.getText().toString();
                String answer = answerEditText.getText().toString();

                if (!question.isEmpty() && !answer.isEmpty()) {
                    Flashcard flashcard = new Flashcard().setFront(question).setBack(answer);
                    // Save the flashcard to the database
                    FlashcardDAO flashcardDAO = RoomDB
                            .getInstance(CreateFlashcardActivity.this).flashcardDAO();
                    flashcardDAO.insert(flashcard);

                    Toast.makeText(CreateFlashcardActivity.this,
                            "Flashcard created!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CreateFlashcardActivity.this,
                            "Please fill in both fields", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}