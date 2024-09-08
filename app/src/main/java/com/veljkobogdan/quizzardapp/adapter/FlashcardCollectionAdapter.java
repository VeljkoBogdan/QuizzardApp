package com.veljkobogdan.quizzardapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.entity.FlashcardCollection;

import java.util.List;

public class FlashcardCollectionAdapter extends RecyclerView.Adapter<FlashcardCollectionAdapter
        .FlashcardCollectionViewHolder> {
    private final List<FlashcardCollection> flashcardCollections;

    public FlashcardCollectionAdapter(List<FlashcardCollection> flashcardCollections) {
        this.flashcardCollections = flashcardCollections;
    }

    @NonNull
    @Override
    public FlashcardCollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flashcard_collection_item, parent, false);
        return new FlashcardCollectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlashcardCollectionViewHolder holder, int position) {
        FlashcardCollection flashcardCollection = flashcardCollections.get(position);
        holder.bind(flashcardCollection);
    }

    @Override
    public int getItemCount() {
        return flashcardCollections.size();
    }

    public static class FlashcardCollectionViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;

        public FlashcardCollectionViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
        }

        public void bind(FlashcardCollection flashcardCollection) {
            nameTextView.setText(flashcardCollection.getName());
        }
    }
}
