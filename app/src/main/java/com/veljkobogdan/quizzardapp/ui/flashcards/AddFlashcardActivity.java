package com.veljkobogdan.quizzardapp.ui.flashcards;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Flashcard;
import com.veljkobogdan.quizzardapp.data.database.entities.Question;
import com.veljkobogdan.quizzardapp.data.models.FlashcardSetWithFlashcards;
import com.veljkobogdan.quizzardapp.data.repository.FlashcardRepository;
import com.veljkobogdan.quizzardapp.data.repository.FlashcardSetRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityAddFlashcardBinding;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

public class AddFlashcardActivity extends AppCompatActivity {

    ActivityAddFlashcardBinding binding;
    EditText termEditText, definitionEditText;
    Button saveButton;
    FlashcardSetRepository flashcardSetRepository;
    FlashcardRepository flashcardRepository;
    FlashcardSetWithFlashcards flashcardSetWithFlashcards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityAddFlashcardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getIntentContent();

        Toolbar toolbar = binding.layoutIncl.toolbar;
        toolbar.setTitle("Add a Flashcard");
        setSupportActionBar(toolbar);

        termEditText = binding.flashcardTerm;
        definitionEditText = binding.flashcardDefinition;
        saveButton = binding.addButton;

        flashcardSetRepository = new FlashcardSetRepository(this);
        flashcardRepository = new FlashcardRepository(this);

        saveButton.setOnClickListener(view -> {
            String termText, definitionText;
            termText = termEditText.getText().toString().trim();
            definitionText = definitionEditText.getText().toString().trim();

            if (termText.isEmpty() || definitionText.isEmpty()) {
                Toast.makeText(AddFlashcardActivity.this, "All fields must be filled",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Flashcard flashcard = new Flashcard();
            flashcard.setDefinition(definitionText);
            flashcard.setTerm(termText);

            flashcardSetRepository.addFlashcardToSet(flashcard,
                    flashcardSetWithFlashcards.flashcardSet.flashcardSetId);

            Toast.makeText(AddFlashcardActivity.this, "Flashcard has been added",
                    Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void getIntentContent() {
        flashcardSetWithFlashcards = (FlashcardSetWithFlashcards) getIntent()
                .getSerializableExtra(IntentGroup.FLASHCARD_SET_WITH_FLASHCARDS);
    }
}