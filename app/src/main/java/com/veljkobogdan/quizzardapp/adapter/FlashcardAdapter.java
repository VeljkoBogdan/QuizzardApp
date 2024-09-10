package com.veljkobogdan.quizzardapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.entity.Flashcard;

import java.util.List;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.ViewHolder> {
    private List<Flashcard> flashcards;

    public FlashcardAdapter(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.flashcard_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Flashcard flashcard = flashcards.get(position);

        holder.front_text.setText(flashcard.getFront());
        holder.back_text.setText(flashcard.getBack());
    }

    @Override
    public int getItemCount() {
        return flashcards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView front_text, back_text;

        public ViewHolder(View itemView) {
            super(itemView);
            front_text = itemView.findViewById(R.id.front_text);
            back_text = itemView.findViewById(R.id.back_text);
        }
    }
}