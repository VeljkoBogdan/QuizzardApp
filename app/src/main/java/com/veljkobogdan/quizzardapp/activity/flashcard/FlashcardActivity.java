package com.veljkobogdan.quizzardapp.activity.flashcard;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.veljkobogdan.quizzardapp.R;

public class FlashcardActivity extends AppCompatActivity {
    ViewFlipper flashcardFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.flashcard_card);

        flashcardFlipper = findViewById(R.id.flashcard_flipper);

        // Set the front and back text
        TextView frontText = findViewById(R.id.front_text);
        frontText.setText("Front side text");

        TextView backText = findViewById(R.id.back_text);
        backText.setText("Back side text");

        // Set the flip animation
        flashcardFlipper.setFlipInterval(1000); // 1 second
        flashcardFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        flashcardFlipper.setOutAnimation(this, android.R.anim.slide_out_right);

        // Flip the flashcard on click
        flashcardFlipper.setOnClickListener(v -> flashcardFlipper.showNext());
    }
}