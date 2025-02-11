package com.veljkobogdan.quizzardapp.ui.flashcards;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
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

import java.util.HashMap;
import java.util.Map;

public class ViewFlashcardSetActivity extends AppCompatActivity {
    private ActivityViewFlashcardSetBinding binding;
    private RecyclerView recycler;
    private FlashcardAdapter flashcardAdapter;
    private FlashcardRepository flashcardRepository;
    private FlashcardSetWithFlashcards flashcardSet;
    private Button learnButton, flashcardsButton;
    private TextView titleTextView;
    private Map<Long, Boolean> flipStates = new HashMap<>();

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
                boolean isFlipped = flipStates.containsKey(flashcard.getFlashcardId())
                        && Boolean.TRUE.equals(flipStates.get(flashcard.getFlashcardId()));

                flipCard(view, view.findViewById(R.id.flashcardTerm), view.findViewById(R.id.flashcardDefinition), isFlipped);

                flipStates.put(flashcard.getFlashcardId(), !isFlipped);
            }

            @Override
            public void onFlashcardLongClick(Flashcard flashcard) {

            }
        });

        recycler.setAdapter(flashcardAdapter);

        loadFlashcards();
    }

    private void flipCard(View view, View frontTextView, View backTextView, boolean isFlipped) {
        float startAngle = 0f;
        float midAngle = 90f;
        float secondStartAngle = 270f;
        float endAngle = 360f;

        float startScale = 1f;
        float midScale = 0.65f;

        long duration = 150;

        ObjectAnimator firstHalfRotation = ObjectAnimator
                .ofFloat(view, "rotationY", startAngle, midAngle)
                .setDuration(duration);

        ObjectAnimator firstHalfScaleX = ObjectAnimator
                .ofFloat(view, "scaleX", startScale, midScale)
                .setDuration(duration);
        ObjectAnimator firstHalfScaleY = ObjectAnimator
                .ofFloat(view, "scaleY", startScale, midScale)
                .setDuration(duration);

        ObjectAnimator secondHalfRotation = ObjectAnimator
                .ofFloat(view, "rotationY", secondStartAngle, endAngle)
                .setDuration(duration);

        ObjectAnimator secondHalfScaleX = ObjectAnimator
                .ofFloat(view, "scaleX", midScale, startScale)
                .setDuration(duration);
        ObjectAnimator secondHalfScaleY = ObjectAnimator
                .ofFloat(view, "scaleY", midScale, startScale)
                .setDuration(duration);

        firstHalfRotation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (isFlipped) {
                    frontTextView.setVisibility(View.VISIBLE);
                    backTextView.setVisibility(View.INVISIBLE);
                } else {
                    frontTextView.setVisibility(View.INVISIBLE);
                    backTextView.setVisibility(View.VISIBLE);
                }

                secondHalfRotation.start();
                secondHalfScaleY.start();
                secondHalfScaleX.start();
            }
        });

        firstHalfRotation.start();
        firstHalfScaleX.start();
        firstHalfScaleY.start();
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