package com.veljkobogdan.quizzardapp.ui.flashcards;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Flashcard;

import java.util.List;

/** @noinspection ClassEscapesDefinedScope*/
public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.FlashcardAdapterViewHolder> {
    private List<Flashcard> flashcards;
    private OnFlashcardClickListener onFlashcardClickListener;

    public FlashcardAdapter(OnFlashcardClickListener onFlashcardClickListener) {
        this.onFlashcardClickListener = onFlashcardClickListener;
    }

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
        notifyItemChanged(R.id.recycler);
    }

    @NonNull
    @Override
    public FlashcardAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_flashcard, parent, false);
        return new FlashcardAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlashcardAdapterViewHolder holder, int position) {
        Flashcard flashcard = flashcards.get(position);
        holder.bind(flashcard);
    }

    @Override
    public int getItemCount() {
        return flashcards.size();
    }

    class FlashcardAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView flashcardTerm, flashcardDefinition;

        public FlashcardAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            flashcardTerm = itemView.findViewById(R.id.flashcardTerm);
            flashcardDefinition = itemView.findViewById(R.id.flashcardDefinition);

            // Handle item clicks
            itemView.setOnClickListener(view -> {
                if (onFlashcardClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onFlashcardClickListener.onFlashcardClick(flashcards.get(position), itemView);
                    }
                }
            });

            // Handle long press
            itemView.setOnLongClickListener(view -> {
                if (onFlashcardClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onFlashcardClickListener.onFlashcardLongClick(flashcards.get(position));
                    }
                }

                return true;
            });
        }

        public void bind(Flashcard flashcard) {
            flashcardTerm.setText(flashcard.getTerm());
            flashcardDefinition.setText(flashcard.getDefinition());
        }
    }

    public interface OnFlashcardClickListener {
        void onFlashcardClick(Flashcard flashcard, View flaschardView);
        void onFlashcardLongClick(Flashcard flashcard);
    }
}
