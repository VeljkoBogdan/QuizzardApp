package com.veljkobogdan.quizzardapp.ui.flashcards;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Flashcard;
import com.veljkobogdan.quizzardapp.data.repository.FlashcardRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityEditFlashcardBinding;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

public class EditFlashcardActivity extends AppCompatActivity {

    EditText term, definition;
    Button saveButton;
    Flashcard flashcard;
    FlashcardRepository flashcardRepository;

    ActivityEditFlashcardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityEditFlashcardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        flashcardRepository = new FlashcardRepository(this);

        Toolbar toolbar = binding.toolbarIncl.toolbar;
        toolbar.setTitle("Editing Flashcard");
        setSupportActionBar(toolbar);

        term = binding.flashcardTerm;
        definition = binding.flashcardDefinition;
        saveButton = binding.addButton;

        saveButton.setOnClickListener(view -> {
            String termText, definitionText;
            termText = term.getText().toString().trim();
            definitionText = definition.getText().toString().trim();

            flashcard.term = termText;
            flashcard.definition = definitionText;

            flashcardRepository.updateFlashcard(flashcard);
            finish();
        });

        getIntentContent();
    }

    public void getIntentContent() {
        flashcard = (Flashcard) getIntent().getSerializableExtra(IntentGroup.FLASHCARD);

        term.setText(flashcard.getTerm());
        definition.setText(flashcard.getDefinition());
    }
}