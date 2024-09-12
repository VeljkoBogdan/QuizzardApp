package com.veljkobogdan.quizzardapp.activity.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.adapter.FlashcardAdapter;
import com.veljkobogdan.quizzardapp.database.FlashcardDAO;
import com.veljkobogdan.quizzardapp.database.RoomDB;
import com.veljkobogdan.quizzardapp.entity.Flashcard;
import com.veljkobogdan.quizzardapp.entity.FlashcardCollection;

import java.io.Serializable;
import java.util.List;

public class FlashcardActivity extends AppCompatActivity {
    ViewFlipper flashcardFlipper;
    RecyclerView recyclerView;
    FlashcardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flashcard);

        TextView title = findViewById(R.id.title);
        title.setText(R.string.flashcards);

        recyclerView = findViewById(R.id.flashcard_recycler_view);
        adapter = new FlashcardAdapter(getFlashcards());
        recyclerView.setAdapter(adapter);

//        flashcardFlipper = findViewById(R.id.flashcard_flipper);
//
//        // Set the front and back text
//        TextView frontText = findViewById(R.id.front_text);
//        frontText.setText("Front side text");
//
//        TextView backText = findViewById(R.id.back_text);
//        backText.setText("Back side text");
//
//        // Set the flip animation
//        flashcardFlipper.setFlipInterval(1000); // 1 second
//        flashcardFlipper.setInAnimation(this, android.R.anim.slide_in_left);
//        flashcardFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
//
//        // Flip the flashcard on click
//        flashcardFlipper.setOnClickListener(v -> flashcardFlipper.showNext());
    }

    private List<Flashcard> getFlashcards() {
        Intent intent = getIntent();
        if (!intent.hasExtra("flashcardCollection")) {
            FlashcardDAO flashcardDAO = RoomDB.getInstance(this).flashcardDAO();
            return flashcardDAO.getAll();
        } else {
            try {
                Serializable list = intent.getSerializableExtra("flashcardCollection");
                FlashcardCollection flashcardCollection = (FlashcardCollection) list;
                if (flashcardCollection != null) {
                    return flashcardCollection.getFlashcards();
                }
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                return null;
            }
        }
        return null;
    }
}