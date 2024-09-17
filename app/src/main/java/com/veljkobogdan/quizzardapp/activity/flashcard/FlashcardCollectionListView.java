package com.veljkobogdan.quizzardapp.activity.flashcard;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.adapter.FlashcardCollectionAdapter;
import com.veljkobogdan.quizzardapp.database.RoomDB;
import com.veljkobogdan.quizzardapp.entity.FlashcardCollection;
import com.veljkobogdan.quizzardapp.helper.RedirectHelper;

import java.util.List;

public class FlashcardCollectionListView extends AppCompatActivity {
    private RecyclerView flashcardCollectionList;
    private FlashcardCollectionAdapter adapter;
    ImageButton image_add, image_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_collection_list_view);

        TextView title = findViewById(R.id.title);
        title.setText(R.string.flashcard_collections);

        image_add = findViewById(R.id.image_add);
        image_back = findViewById(R.id.image_back);
        image_add.setOnClickListener(v -> {
            RedirectHelper.toNewFlashcardCollectionActivity(this, 0);
        });
        image_back.setOnClickListener(v -> {
            finish();
        });

        flashcardCollectionList = findViewById(R.id.flashcard_collection_list);
        flashcardCollectionList.setLayoutManager(
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        // Initialize the adapter with your data
        adapter = new FlashcardCollectionAdapter(getFlashcardCollections());
        flashcardCollectionList.setAdapter(adapter);

        flashcardCollectionList.setOnClickListener(v -> {
            RedirectHelper.toFlashcardCollectionActivity(this, 0);
        });
    }

    // Get the list of Flashcard collections from your database or other data source
    private List<FlashcardCollection> getFlashcardCollections() {
        RoomDB db = RoomDB.getInstance(this);
        return db.flashcardCollectionDAO().getAll();
    }
}