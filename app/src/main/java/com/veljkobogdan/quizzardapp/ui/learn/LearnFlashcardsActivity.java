package com.veljkobogdan.quizzardapp.ui.learn;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Flashcard;
import com.veljkobogdan.quizzardapp.data.models.FlashcardSetWithFlashcards;
import com.veljkobogdan.quizzardapp.databinding.ActivityLearnFlashcardsBinding;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LearnFlashcardsActivity extends AppCompatActivity {
    private ActivityLearnFlashcardsBinding binding;
    private FlashcardSetWithFlashcards flashcardSet;
    private List<Flashcard> flashcards = new ArrayList<>();
    private TextView completionStatusText, knowStatusText, dontKnowStatusText;
    private FrameLayout frameLayout;

    private int knownCount = 0, unknownCount = 0;
    private int currentIndex = 0;
    private boolean isFlipped = true;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityLearnFlashcardsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        frameLayout = binding.frame;

        getIntentContent();

        flashcards = flashcardSet.flashcards;
        knowStatusText = binding.knowStatusNumber;
        dontKnowStatusText = binding.dontKnowStatusNumber;
        completionStatusText = binding.flashcardCompletionStatusText;

        Toolbar toolbar = binding.toolbarIncl.toolbar;
        toolbar.setTitle("Learn");
        setSupportActionBar(toolbar);

        notifyStatusChanged();
        showNextFlashcard();
    }

    private void getIntentContent() {
        flashcardSet = (FlashcardSetWithFlashcards) getIntent()
                .getSerializableExtra(IntentGroup.FLASHCARD_SET_WITH_FLASHCARDS);

        if (flashcardSet == null) {
            Log.e("ERROR", "Flashcard set is null!");
            finish();
        }
    }

    @SuppressLint("SetTextI18n") // We don't need locale because these are numbers
    public void notifyStatusChanged() {
        dontKnowStatusText.setText(Integer.toString(unknownCount));
        knowStatusText.setText(Integer.toString(knownCount));
        String status = currentIndex + "/" + flashcards.size() + " completed";
        completionStatusText.setText(status);
    }

    public void showNextFlashcard() {
        if (currentIndex >= flashcards.size()) {
            showResults();
            return;
        }

        isFlipped = true;

        Flashcard flashcard = flashcards.get(currentIndex);
        View flashcardView = LayoutInflater.from(this)
                .inflate(R.layout.item_learn_flashcard, null);
        TextView term = flashcardView.findViewById(R.id.flashcardTerm);
        TextView definition = flashcardView.findViewById(R.id.flashcardDefinition);

        term.setText(flashcard.getTerm());
        definition.setText(flashcard.getDefinition());

        flashcardView.setClickable(true);
        flashcardView.setFocusable(true);

        GestureDetector gestureDetector = new GestureDetector(this, new FlashcardGestureListener());
        flashcardView.setOnTouchListener((v, e) -> gestureDetector.onTouchEvent(e));
        flashcardView.setOnClickListener(v -> {
            flipCard();
            isFlipped = !isFlipped;
        });

        frameLayout.removeAllViews();
        frameLayout.addView(flashcardView);
    }

    private void flipCard() {
        View view = frameLayout.getChildAt(0);
        if (view == null) return;

        TextView term = view.findViewById(R.id.flashcardTerm);
        TextView definition = view.findViewById(R.id.flashcardDefinition);

        float startAngle = 0f;
        float midAngle = 90f;
        float secondStartAngle = 270f;
        float endAngle = 360f;

        float startScale = 1f;
        float midScale = 0.40f;

        long duration = 200;

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
                    term.setVisibility(View.VISIBLE);
                    definition.setVisibility(View.INVISIBLE);
                } else {
                    term.setVisibility(View.INVISIBLE);
                    definition.setVisibility(View.VISIBLE);
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

    private void showResults() {
        // TODO: Handle results after finishing learning
    }

    private class FlashcardGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 200; // Minimum distance for swipe
        private static final int SWIPE_VELOCITY_THRESHOLD = 200; // Minimum velocity for swipe

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float deltaX = e2.getX() - e1.getX();

            if (Math.abs(deltaX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (deltaX > 0) {
                    correctAnswer();
                } else {
                    wrongAnswer();
                }
                return true;
            }
            return false;
        }
    }

    private void correctAnswer() {
        knownCount++;
        animateFlashcard(1);
        notifyStatusChanged();
    }

    private void wrongAnswer() {
        unknownCount++;
        animateFlashcard(-1);
        notifyStatusChanged();
    }

    private void animateFlashcard(int direction) {
        View card = frameLayout.getChildAt(0);
        if (card == null) return;

        card.animate()
                .translationX(direction * card.getWidth() * 2f)
                .alpha(0.5f)
                .setDuration(250)
                .withEndAction(() -> {
                    currentIndex++;
                    notifyStatusChanged();
                    showNextFlashcard();
                })
                .start();
    }
}