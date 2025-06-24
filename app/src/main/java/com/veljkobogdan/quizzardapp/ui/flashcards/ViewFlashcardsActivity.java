package com.veljkobogdan.quizzardapp.ui.flashcards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Flashcard;
import com.veljkobogdan.quizzardapp.data.models.FlashcardSetWithFlashcards;
import com.veljkobogdan.quizzardapp.databinding.ActivityViewFlashcardsBinding;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

public class ViewFlashcardsActivity extends AppCompatActivity {

    ActivityViewFlashcardsBinding binding;
    RecyclerView recyclerView;
    FlashcardSetWithFlashcards flashcardSetWithFlashcards;
    FlashcardAdapter flashcardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityViewFlashcardsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = binding.toolbarIncl.toolbar;
        toolbar.setTitle("Flashcards");
        setSupportActionBar(toolbar);

        flashcardAdapter = new FlashcardAdapter(new FlashcardAdapter.OnFlashcardClickListener() {
            @Override
            public void onFlashcardClick(Flashcard flashcard, View flaschardView) {
                // TODO: Click to edit the flashcard
            }

            @Override
            public void onFlashcardLongClick(Flashcard flashcard) {
                // TODO: Delete or edit flashcard
            }
        });
        recyclerView = binding.recycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(flashcardAdapter);

        getIntentContent();
        loadFlashcards();
    }

    private void getIntentContent() {
        Intent i = getIntent();
        flashcardSetWithFlashcards = (FlashcardSetWithFlashcards) i.getSerializableExtra(IntentGroup.FLASHCARD_SET_WITH_FLASHCARDS);
    }

    private void loadFlashcards() {
        flashcardAdapter.setFlashcards(flashcardSetWithFlashcards.flashcards);
    }
}