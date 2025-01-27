package com.veljkobogdan.quizzardapp.ui.flashcards;

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
import com.veljkobogdan.quizzardapp.data.database.entities.FlashcardSet;
import com.veljkobogdan.quizzardapp.data.models.FlashcardSetWithFlashcards;
import com.veljkobogdan.quizzardapp.data.repository.FlashcardSetRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityFlashcardSetsBinding;

import java.util.List;

public class FlashcardSetsActivity extends AppCompatActivity {
    ActivityFlashcardSetsBinding binding;
    RecyclerView recycler;
    FlashcardSetAdapter flashcardSetAdapter;
    FlashcardSetRepository flashcardSetRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityFlashcardSetsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = binding.toolbarIncl.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setTitle("Flashcard Sets");

        recycler = binding.recycler;
        recycler.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false));

        flashcardSetAdapter = new FlashcardSetAdapter(new FlashcardSetAdapter.OnFlashcardSetClickListener() {
            // TODO: handle fc set clicks
            @Override
            public void onClickListener(FlashcardSetWithFlashcards flashcardSetWithFlashcards) {

            }

            @Override
            public void onLongClickListener(FlashcardSetWithFlashcards flashcardSetWithFlashcards) {

            }
        });

        recycler.setAdapter(flashcardSetAdapter);

        loadFlashcards();
    }

    private void loadFlashcards() {
        flashcardSetRepository.getFlashcardSetsWithFlashcards().observe(this, flashcardSets -> {
            if (!flashcardSets.isEmpty()) {
                flashcardSetAdapter.setFlashcardSets(flashcardSets);
                binding.noFlashcardSetsText.setVisibility(View.GONE);
            } else {
                binding.noFlashcardSetsText.setVisibility(View.VISIBLE);
            }
        });
    }
}