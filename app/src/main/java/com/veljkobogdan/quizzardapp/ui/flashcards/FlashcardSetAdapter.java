package com.veljkobogdan.quizzardapp.ui.flashcards;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.models.FlashcardSetWithFlashcards;

import java.util.ArrayList;
import java.util.List;

/** @noinspection ClassEscapesDefinedScope*/
public class FlashcardSetAdapter extends RecyclerView.Adapter<FlashcardSetAdapter.FlashcardSetViewHolder> {
    public List<FlashcardSetWithFlashcards> flashcardSets = new ArrayList<>();
    public OnFlashcardSetClickListener onFlashcardSetClickListener;

    public FlashcardSetAdapter(OnFlashcardSetClickListener onFlashcardSetClickListener) {
        this.onFlashcardSetClickListener = onFlashcardSetClickListener;
    }

    public void setFlashcardSets(List<FlashcardSetWithFlashcards> flashcardSets) {
        this.flashcardSets = flashcardSets;
        notifyItemChanged(R.id.recycler);
    }

    @NonNull
    @Override
    public FlashcardSetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_flashcard_set, parent, false);

        return new FlashcardSetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FlashcardSetViewHolder holder, int position) {
        FlashcardSetWithFlashcards flashcardSet = flashcardSets.get(position);
        holder.bind(flashcardSet);
    }

    @Override
    public int getItemCount() {
        return flashcardSets.size();
    }

    class FlashcardSetViewHolder extends RecyclerView.ViewHolder {
        TextView flashcardSetTitle;
        TextView flashcardSetContent;

        public FlashcardSetViewHolder(@NonNull View itemView) {
            super(itemView);

            flashcardSetTitle = itemView.findViewById(R.id.flashcardSetTitle);
            flashcardSetContent = itemView.findViewById(R.id.flashcardSetContent);

            // Handle item clicks
            itemView.setOnClickListener(view -> {
                if (onFlashcardSetClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onFlashcardSetClickListener.onClickListener(flashcardSets.get(position));
                    }
                }
            });

            // Handle long press
            itemView.setOnLongClickListener(view -> {
                if (onFlashcardSetClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onFlashcardSetClickListener.onLongClickListener(flashcardSets.get(position));
                    }
                }

                return true;
            });
        }

        public void bind(FlashcardSetWithFlashcards flashcardSet) {
            flashcardSetTitle.setText(flashcardSet.flashcardSet.name);
            flashcardSetContent.setText(String.format("Size: %d", flashcardSet.flashcards.size()));
        }
    }

    public interface OnFlashcardSetClickListener {
        void onClickListener(FlashcardSetWithFlashcards flashcardSetWithFlashcards);
        void onLongClickListener(FlashcardSetWithFlashcards flashcardSetWithFlashcards);
    }
}
