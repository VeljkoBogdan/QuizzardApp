package com.veljkobogdan.quizzardapp.ui.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Tag;
import com.veljkobogdan.quizzardapp.data.models.FlashcardSetWithFlashcards;
import com.veljkobogdan.quizzardapp.data.models.NoteWithTags;
import com.veljkobogdan.quizzardapp.data.repository.NoteRepository;

import java.util.ArrayList;
import java.util.List;

/** @noinspection ClassEscapesDefinedScope*/
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    public List<NoteWithTags> notes = new ArrayList<>();
    public final OnNoteClickListener onNoteClickListener;
    private final Context context;
    private final NoteRepository noteRepository;

    public NoteAdapter(Context context, OnNoteClickListener onNoteClickListener) {
        this.context = context;
        this.onNoteClickListener = onNoteClickListener;

        noteRepository = new NoteRepository(context);
    }

    public void setNotes(List<NoteWithTags> notes) {
        this.notes = notes;
        notifyItemChanged(R.id.recycler);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteWithTags note = notes.get(position);
        holder.bind(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void updateNotes(List<NoteWithTags> newNotes) {
        DiffUtil.Callback diffCallback = new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return notes.size();
            }

            @Override
            public int getNewListSize() {
                return newNotes.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return notes.get(oldItemPosition).note.getNoteId() ==
                        newNotes.get(newItemPosition).note.getNoteId();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return notes.get(oldItemPosition).equals(newNotes.get(newItemPosition));
            }
        };

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback);
        notes.clear();
        notes.addAll(newNotes);
        result.dispatchUpdatesTo(this);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView content;
        private final LinearLayout tagLayout;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.noteTitle);
            content = itemView.findViewById(R.id.noteContent);
            tagLayout = itemView.findViewById(R.id.tagLayout);

            // Handle item clicks
            itemView.setOnClickListener(view -> {
                if (onNoteClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onNoteClickListener.onNoteClick(notes.get(position));
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

            menu.getMenuInflater().inflate(R.menu.note_popup_menu, menu.getMenu());
            menu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.delete) {
                    NoteWithTags setToDelete = notes.get(position);
                    noteRepository.deleteNoteWithTags(setToDelete);
                    notes.remove(setToDelete);
                    notifyItemRemoved(position);
                    return true;
                }
                return false;
            });

            menu.show();
        }

        public void bind(NoteWithTags note) {
            title.setText(note.note.getTitle());
            content.setText(note.note.getContent());

            tagLayout.removeAllViews();

            LayoutInflater layoutInflater = LayoutInflater.from(itemView.getContext());
            for (Tag tag : note.tags) {
                View tagView = layoutInflater.inflate(R.layout.item_tag, tagLayout, false);

                TextView textView = tagView.findViewById(R.id.tagTextView);
                textView.setText(tag.getName());

                tagLayout.addView(tagView);
            }
        }
    }

    public interface OnNoteClickListener {
        void onNoteClick(NoteWithTags note);
    }
}
