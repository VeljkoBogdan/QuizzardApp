package com.veljkobogdan.quizzardapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.entity.Flashcard;

import java.util.List;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder> {
    private final List<Flashcard> flashcards;
    Context context;

    public FlashcardAdapter(Context context, List<Flashcard> flashcards) {
        this.flashcards = flashcards;
        this.context = context;
    }

    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FlashcardViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.flashcard_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FlashcardViewHolder holder, int position) {
        Flashcard flashcard = flashcards.get(position);

        holder.front_text.setText(flashcard.getFront());
        holder.back_text.setText(flashcard.getBack());

        holder.flashcard_flipper.setVisibility(View.VISIBLE);
        holder.flashcard_flipper.setFlipInterval(1000); // 1 second
        holder.flashcard_flipper.setInAnimation(holder.flashcard_flipper.getContext(), android.R.anim.slide_in_left);
        holder.flashcard_flipper.setOutAnimation(holder.flashcard_flipper.getContext(), android.R.anim.slide_out_right);
        holder.flashcard_flipper.setOnClickListener(v -> holder.flashcard_flipper.showNext());
    }

    @Override
    public int getItemCount() {
        return flashcards.size();
    }

    public static class FlashcardViewHolder extends RecyclerView.ViewHolder {
        public TextView front_text, back_text;
        public ViewFlipper flashcard_flipper;

        public FlashcardViewHolder(View itemView) {
            super(itemView);
            front_text = itemView.findViewById(R.id.front_text);
            back_text = itemView.findViewById(R.id.back_text);
            flashcard_flipper = itemView.findViewById(R.id.flashcard_flipper);
        }
    }
}