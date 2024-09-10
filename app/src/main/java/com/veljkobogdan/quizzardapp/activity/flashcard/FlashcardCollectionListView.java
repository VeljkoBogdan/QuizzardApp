package com.veljkobogdan.quizzardapp.activity.flashcard;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.adapter.FlashcardCollectionAdapter;
import com.veljkobogdan.quizzardapp.database.RoomDB;
import com.veljkobogdan.quizzardapp.entity.FlashcardCollection;

import java.util.List;

public class FlashcardCollectionListView extends AppCompatActivity {
    private RecyclerView flashcardCollectionList;
    private FlashcardCollectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_collection_list_view);

        flashcardCollectionList = findViewById(R.id.flashcard_collection_list);
        flashcardCollectionList.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter with your data
        adapter = new FlashcardCollectionAdapter(getFlashcardCollections());
        flashcardCollectionList.setAdapter(adapter);

        flashcardCollectionList.setOnClickListener(v -> {

        });
    }

    // Get the list of Flashcard collections from your database or other data source
    private List<FlashcardCollection> getFlashcardCollections() {
        RoomDB db = RoomDB.getInstance(this);
        return db.flashcardCollectionDAO().getAll();
    }
}