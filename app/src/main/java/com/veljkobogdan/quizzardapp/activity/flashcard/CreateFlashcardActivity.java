package com.veljkobogdan.quizzardapp.activity.flashcard;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.database.FlashcardCollectionDAO;
import com.veljkobogdan.quizzardapp.database.FlashcardDAO;
import com.veljkobogdan.quizzardapp.database.RoomDB;
import com.veljkobogdan.quizzardapp.entity.Flashcard;
import com.veljkobogdan.quizzardapp.entity.FlashcardCollection;

import java.util.ArrayList;
import java.util.List;

public class CreateFlashcardActivity extends AppCompatActivity {
    EditText questionEditText;
    EditText answerEditText;
    ImageButton image_add, image_back;
    TextView title;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_create_flashcard);

            initWidgets();

            initSpinner();

            image_back.setOnClickListener(v -> finish());
            image_add.setOnClickListener(this::onAddClick);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void initWidgets() {
        questionEditText = findViewById(R.id.question_edit_text);
        answerEditText = findViewById(R.id.answer_edit_text);

        image_add = findViewById(R.id.image_add);
        image_back = findViewById(R.id.image_back);

        title = findViewById(R.id.title);
        title.setText(R.string.create_flashcard);
    }

    private void initSpinner() {
        spinner = findViewById(R.id.collection_spinner);

        RoomDB db = RoomDB.getInstance(this);
        List<FlashcardCollection> flashcardCollections = db.flashcardCollectionDAO().getAll();
        ArrayAdapter<FlashcardCollection> adapter = new ArrayAdapter<>(this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                flashcardCollections);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    private void onAddClick(View v) {
        String question = questionEditText.getText().toString();
        String answer = answerEditText.getText().toString();

        if (!question.isEmpty() && !answer.isEmpty() && spinner.getSelectedItem() != null) {
            try {
                Flashcard flashcard = new Flashcard().setFront(question).setBack(answer);
                // Get DAOs
                FlashcardDAO flashcardDAO = RoomDB
                        .getInstance(CreateFlashcardActivity.this).flashcardDAO();
                FlashcardCollectionDAO flashcardCollectionDao = RoomDB
                        .getInstance(CreateFlashcardActivity.this).flashcardCollectionDAO();
                // Get flashcard collection
                FlashcardCollection flashcardCollection = (FlashcardCollection) spinner.getSelectedItem();
                // Get flashcards in collection or create new collection if empty
                ArrayList<Flashcard> flashcards;
                if (flashcardCollection.getFlashcards() == null) {
                    flashcards = new ArrayList<>();
                } else {
                    flashcards = flashcardCollection.getFlashcards();
                }
                // Add flashcard to collection
                flashcards.add(flashcard);
                // Update flashcard collection
                flashcardCollectionDao.update(flashcardCollection.getId(), flashcardCollection.getName(), flashcards);
                // Add flashcard to database
                flashcardDAO.insert(flashcard);

                Toast.makeText(CreateFlashcardActivity.this,
                        "Flashcard created!", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                Toast.makeText(CreateFlashcardActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("RUNTIME_ERROR", e.getMessage());
            }
        } else {
            Toast.makeText(CreateFlashcardActivity.this,
                    "Please fill in both fields", Toast.LENGTH_SHORT).show();
        }
    }
}