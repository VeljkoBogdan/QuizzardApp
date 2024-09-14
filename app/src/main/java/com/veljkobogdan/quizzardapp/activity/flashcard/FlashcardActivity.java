package com.veljkobogdan.quizzardapp.activity.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.adapter.FlashcardAdapter;
import com.veljkobogdan.quizzardapp.database.FlashcardDAO;
import com.veljkobogdan.quizzardapp.database.RoomDB;
import com.veljkobogdan.quizzardapp.entity.Flashcard;
import com.veljkobogdan.quizzardapp.entity.FlashcardCollection;
import com.veljkobogdan.quizzardapp.helper.RedirectHelper;

import java.io.Serializable;
import java.util.List;

public class FlashcardActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageButton image_add, image_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        TextView title = findViewById(R.id.title);
        title.setText(R.string.flashcards);

        image_add = findViewById(R.id.image_add);
        image_back = findViewById(R.id.image_back);

        image_add.setOnClickListener(v -> RedirectHelper.toNewFlashcardActivity(this, 0));
        image_back.setOnClickListener(v -> finish());

        try {
            recyclerView = findViewById(R.id.flashcard_recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
            recyclerView.setAdapter(new FlashcardAdapter(getApplicationContext(), getFlashcards()));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
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