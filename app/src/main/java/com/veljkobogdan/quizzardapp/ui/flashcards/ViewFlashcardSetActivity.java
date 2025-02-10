package com.veljkobogdan.quizzardapp.ui.flashcards;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import com.veljkobogdan.quizzardapp.data.repository.FlashcardRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityViewFlashcardSetBinding;

public class ViewFlashcardSetActivity extends AppCompatActivity {
    private ActivityViewFlashcardSetBinding binding;
    private RecyclerView recycler;
    private FlashcardAdapter flashcardAdapter;
    private FlashcardRepository flashcardRepository;
    private FlashcardSetWithFlashcards flashcardSet;
    private Button learnButton, flashcardsButton;
    private TextView titleTextView;

    public static final String FLASHCARD_SET = "flashcardSet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityViewFlashcardSetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getIntentContent();
        setLayoutContent();

        flashcardRepository = new FlashcardRepository(this);

        Toolbar toolbar = binding.toolbarIncl.toolbar;
        toolbar.setTitle("Flashcard Set");
        setSupportActionBar(toolbar);

        recycler = binding.recycler;
        recycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        flashcardAdapter = new FlashcardAdapter(new FlashcardAdapter.OnFlashcardClickListener() {
            @Override
            public void onFlashcardClick(Flashcard flashcard, View view) {
                if (view.findViewById(R.id.flashcardTerm).getVisibility() == View.VISIBLE){
                    view.findViewById(R.id.flashcardTerm).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.flashcardDefinition).setVisibility(View.VISIBLE);
                } else {
                    view.findViewById(R.id.flashcardTerm).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.flashcardDefinition).setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFlashcardLongClick(Flashcard flashcard) {

            }
        });

        recycler.setAdapter(flashcardAdapter);

        loadFlashcards();
    }

    private void setLayoutContent() {
        titleTextView = binding.flashcardSetTitle;
        learnButton = binding.learnButton;
        flashcardsButton = binding.flashcardsButton;

        titleTextView.setText(flashcardSet.flashcardSet.name);
    }

    private void getIntentContent() {
        try {
            flashcardSet = (FlashcardSetWithFlashcards) getIntent()
                    .getSerializableExtra(FLASHCARD_SET);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            finish();
        }
    }

    private void loadFlashcards() {
        flashcardAdapter.setFlashcards(flashcardSet.flashcards);
    }
}