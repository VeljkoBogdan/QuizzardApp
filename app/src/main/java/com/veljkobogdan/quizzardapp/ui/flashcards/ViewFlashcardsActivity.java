package com.veljkobogdan.quizzardapp.ui.flashcards;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Flashcard;
import com.veljkobogdan.quizzardapp.data.models.FlashcardSetWithFlashcards;
import com.veljkobogdan.quizzardapp.data.repository.FlashcardRepository;
import com.veljkobogdan.quizzardapp.data.repository.FlashcardSetRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityViewFlashcardsBinding;
import com.veljkobogdan.quizzardapp.ui.sets.FlashcardSetAdapter;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

public class ViewFlashcardsActivity extends AppCompatActivity {

    ActivityViewFlashcardsBinding binding;
    RecyclerView recyclerView;
    FlashcardSetWithFlashcards flashcardSetWithFlashcards;
    FlashcardAdapter flashcardAdapter;
    FlashcardRepository flashcardRepository;
    FlashcardSetRepository flashcardSetRepository;

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

        flashcardRepository = new FlashcardRepository(this);
        flashcardSetRepository = new FlashcardSetRepository(this);

        Toolbar toolbar = binding.toolbarIncl.toolbar;
        toolbar.setTitle("Flashcards");
        setSupportActionBar(toolbar);

        flashcardAdapter = new FlashcardAdapter(new FlashcardAdapter.OnFlashcardClickListener() {
            @Override
            public void onFlashcardClick(Flashcard flashcard, View flaschardView) {
                Intent i = new Intent(ViewFlashcardsActivity.this, EditFlashcardActivity.class);
                i.putExtra(IntentGroup.FLASHCARD, flashcard);
                startActivity(i);
            }

            @Override
            public void onFlashcardLongClick(Flashcard flashcard, View flashcardView) {
                PopupMenu menu = new PopupMenu(ViewFlashcardsActivity.this, flashcardView);

                menu.getMenuInflater().inflate(R.menu.set_popup_menu, menu.getMenu());
                menu.setOnMenuItemClickListener(menuItem -> {
                    if (menuItem.getItemId() == R.id.delete) {
                        try {
                            flashcardRepository.deleteFlashcard(flashcard);
                        } catch (Exception e) {
                            Log.e("ERROR", e.getMessage());
                        }
                        return true;
                    }
                    return false;
                });

                menu.show();
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
        flashcardSetRepository
                .getFlashcardSetWithFlashcards(flashcardSetWithFlashcards.flashcardSet.flashcardSetId)
                .observe(ViewFlashcardsActivity.this, newFlashcardSet -> {
                    if (!newFlashcardSet.flashcards.isEmpty()) {
                        flashcardAdapter.setFlashcards(newFlashcardSet.flashcards);
                    }
                });
    }
}