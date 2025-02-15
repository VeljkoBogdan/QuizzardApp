package com.veljkobogdan.quizzardapp.ui.sets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Flashcard;
import com.veljkobogdan.quizzardapp.data.database.entities.FlashcardSet;
import com.veljkobogdan.quizzardapp.data.repository.FlashcardSetRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityAddFlashcardSetBinding;

import java.util.ArrayList;
import java.util.List;

public class AddFlashcardSetActivity extends AppCompatActivity {
    private ActivityAddFlashcardSetBinding binding;
    private EditText title;
    private LinearLayout flashcardLayout;
    private Button saveButton, addFlashcardButton;
    private final List<Flashcard> flashcards = new ArrayList<>();
    private FlashcardSetRepository setRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityAddFlashcardSetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = binding.toolbarIncl.toolbar;
        toolbar.setTitle("New Flashcard Set");
        setSupportActionBar(toolbar);

        title = binding.title;
        flashcardLayout = binding.flashcardLayout;
        saveButton = binding.addButton;
        setRepository = new FlashcardSetRepository(this);
        addFlashcardButton = binding.newFlashcardButton;

        saveButton.setOnClickListener(view -> saveFlashcards());
        addFlashcardButton.setOnClickListener(view -> addFlashcard());

        addFlashcard();
        addFlashcard();
    }

    private void addFlashcard() {
        View flashcard = LayoutInflater.from(this)
                .inflate(R.layout.item_flashcard_edit, flashcardLayout, false);

        View deleteButton = flashcard.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(v -> {
            flashcardLayout.removeView(flashcard);
        });

        flashcardLayout.addView(flashcard);
    }

    private void saveFlashcards() {
        String titleText = title.getText().toString().trim();

        if (titleText.isEmpty()) {
            title.setError("Title is required!");
            return;
        }

        flashcards.clear();
        for (int i = 0; i < flashcardLayout.getChildCount(); i++) {
            View flashcard = flashcardLayout.getChildAt(i);
            EditText term = flashcard.findViewById(R.id.flashcardTerm);
            EditText definition = flashcard.findViewById(R.id.flashcardDefinition);

            String termText = term.getText().toString().trim();
            String definitionText = definition.getText().toString().trim();

            if (termText.isEmpty()) {
                term.setError("Term cannot be empty!");
                return;
            } else if (definitionText.isEmpty()) {
                definition.setError("Definition cannot be empty!");
                return;
            }

            flashcards.add(new Flashcard(termText, definitionText));
        }

        if (flashcards.size() < 2) {
            Toast.makeText(this, "At least 2 flashcards required", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        setRepository.insertFlashcardSetWithFlashcards(new FlashcardSet(titleText), flashcards);

        finish();
    }
}