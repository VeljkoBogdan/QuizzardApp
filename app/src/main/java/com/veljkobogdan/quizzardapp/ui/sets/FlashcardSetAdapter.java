package com.veljkobogdan.quizzardapp.ui.sets;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.models.FlashcardSetWithFlashcards;
import com.veljkobogdan.quizzardapp.data.repository.FlashcardSetRepository;

import java.util.ArrayList;
import java.util.List;

/** @noinspection ClassEscapesDefinedScope*/
public class FlashcardSetAdapter extends RecyclerView.Adapter<FlashcardSetAdapter.FlashcardSetViewHolder> {
    public List<FlashcardSetWithFlashcards> flashcardSets = new ArrayList<>();
    public OnFlashcardSetClickListener onFlashcardSetClickListener;
    private Context context;
    private FlashcardSetRepository flashcardSetRepository;

    public FlashcardSetAdapter(Context context, OnFlashcardSetClickListener onFlashcardSetClickListener) {
        this.context = context;
        this.onFlashcardSetClickListener = onFlashcardSetClickListener;

        this.flashcardSetRepository = new FlashcardSetRepository(context);
    }

    @Deprecated
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

    public void updateFlashcardSet(List<FlashcardSetWithFlashcards> newFlashcardSets) {
        DiffUtil.Callback diffCallback = new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return flashcardSets.size();
            }

            @Override
            public int getNewListSize() {
                return newFlashcardSets.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return flashcardSets.get(oldItemPosition).flashcardSet.getFlashcardSetId() ==
                        newFlashcardSets.get(newItemPosition).flashcardSet.getFlashcardSetId();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return flashcardSets.get(oldItemPosition).equals(newFlashcardSets.get(newItemPosition));
            }
        };

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback);
        flashcardSets.clear();
        flashcardSets.addAll(newFlashcardSets);
        result.dispatchUpdatesTo(this);
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
                showPopupMenu(view, getAdapterPosition());
                return true;
            });
        }

        private void showPopupMenu(View view, int position) {
            PopupMenu menu = new PopupMenu(context, view);

            menu.getMenuInflater().inflate(R.menu.set_popup_menu, menu.getMenu());
            menu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.delete) {
                    try {
                        FlashcardSetWithFlashcards setToDelete = flashcardSets.get(position);
                        flashcardSetRepository.deleteFlashcardSetWithFlashcards(setToDelete);
                        flashcardSets.remove(setToDelete);
                        notifyItemRemoved(position);
                    } catch (Exception e) {
                        Log.e("ERROR", e.getMessage());
                    }
                    return true;
                }
                return false;
            });

            menu.show();
        }

        public void bind(FlashcardSetWithFlashcards flashcardSet) {
            flashcardSetTitle.setText(flashcardSet.flashcardSet.name);
            flashcardSetContent.setText(String.format("Size: %d", flashcardSet.flashcards.size()));
        }
    }

    public interface OnFlashcardSetClickListener {
        void onClickListener(FlashcardSetWithFlashcards flashcardSetWithFlashcards);
    }
}
