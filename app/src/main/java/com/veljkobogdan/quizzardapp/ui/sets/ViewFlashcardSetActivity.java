package com.veljkobogdan.quizzardapp.ui.sets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
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
import com.veljkobogdan.quizzardapp.data.repository.FlashcardSetRepository;
import com.veljkobogdan.quizzardapp.databinding.ActivityViewFlashcardSetBinding;
import com.veljkobogdan.quizzardapp.ui.flashcards.EditFlashcardActivity;
import com.veljkobogdan.quizzardapp.ui.flashcards.FlashcardFlippableAdapter;
import com.veljkobogdan.quizzardapp.ui.flashcards.ViewFlashcardsActivity;
import com.veljkobogdan.quizzardapp.ui.learn.LearnFlashcardsActivity;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

import java.util.HashMap;
import java.util.Map;

public class ViewFlashcardSetActivity extends AppCompatActivity {
    private ActivityViewFlashcardSetBinding binding;
    private RecyclerView recycler;
    private FlashcardFlippableAdapter flashcardFlippableAdapter;
    private FlashcardRepository flashcardRepository;
    private FlashcardSetRepository flashcardSetRepository;
    private FlashcardSetWithFlashcards flashcardSet;
    private Button learnButton, flashcardsButton;
    private TextView titleTextView;
    private final Map<Long, Boolean> flipStates = new HashMap<>();

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

        learnButton.setOnClickListener(view -> {
            Intent i = new Intent(ViewFlashcardSetActivity.this, LearnFlashcardsActivity.class);
            i.putExtra(IntentGroup.FLASHCARD_SET_WITH_FLASHCARDS, flashcardSet);
            startActivity(i);
        });

        flashcardsButton.setOnClickListener(view -> {
            Intent i = new Intent(ViewFlashcardSetActivity.this, ViewFlashcardsActivity.class);
            i.putExtra(IntentGroup.FLASHCARD_SET_WITH_FLASHCARDS, flashcardSet);
            startActivity(i);
        });

        flashcardRepository = new FlashcardRepository(this);
        flashcardSetRepository = new FlashcardSetRepository(this);

        Toolbar toolbar = binding.toolbarIncl.toolbar;
        toolbar.setTitle(flashcardSet.flashcardSet.name);
        setSupportActionBar(toolbar);

        recycler = binding.recycler;
        recycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        flashcardFlippableAdapter = new FlashcardFlippableAdapter(new FlashcardFlippableAdapter.OnFlashcardClickListener() {

            @Override
            public void onFlashcardClick(Flashcard flashcard, View view) {
                boolean isFlipped = flipStates.containsKey(flashcard.getFlashcardId())
                        && Boolean.TRUE.equals(flipStates.get(flashcard.getFlashcardId()));

                flipCard(view, view.findViewById(R.id.flashcardTerm), view.findViewById(R.id.flashcardDefinition), isFlipped);

                flipStates.put(flashcard.getFlashcardId(), !isFlipped);
            }

            @Override
            public void onFlashcardLongClick(Flashcard flashcard, View view) {
                PopupMenu menu = new PopupMenu(ViewFlashcardSetActivity.this, view);

                menu.getMenuInflater().inflate(R.menu.flashcard_popup_menu, menu.getMenu());
                menu.setOnMenuItemClickListener(menuItem -> {
                    if (menuItem.getItemId() == R.id.edit) {
                        Intent i = new Intent(ViewFlashcardSetActivity.this, EditFlashcardActivity.class);
                        i.putExtra(IntentGroup.FLASHCARD, flashcard);
                        startActivity(i);
                        return true;
                    }
                    if (menuItem.getItemId() == R.id.delete) {
                        flashcardRepository.deleteFlashcard(flashcard);
                        loadFlashcards(flashcardSet.flashcardSet.flashcardSetId);
                        return true;
                    }
                    return false;
                });

                menu.show();
            }
        });

        recycler.setAdapter(flashcardFlippableAdapter);

        loadFlashcards(flashcardSet.flashcardSet.flashcardSetId);
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
                    .getSerializableExtra(IntentGroup.FLASHCARD_SET);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            finish();
        }
    }

    private void loadFlashcards(long setId) {
        flashcardSetRepository.getFlashcardSetWithFlashcards(setId)
                .observe(this, updatedSet -> {
                    if (updatedSet != null) {
                        flashcardFlippableAdapter.setFlashcards(updatedSet.flashcards);
                    }
        });
    }
}