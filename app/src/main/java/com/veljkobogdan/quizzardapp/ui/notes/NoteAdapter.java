package com.veljkobogdan.quizzardapp.ui.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Tag;
import com.veljkobogdan.quizzardapp.data.models.NoteWithTags;

import java.util.ArrayList;
import java.util.List;

/** @noinspection ClassEscapesDefinedScope*/
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    public List<NoteWithTags> notes = new ArrayList<>();
    public final OnNoteClickListener onNoteClickListener;

    public NoteAdapter(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
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
                if (onNoteClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onNoteClickListener.onNoteLongClick(notes.get(position), itemView);
                    }
                }

                return true;
            });
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
        void onNoteLongClick(NoteWithTags note, View view);
    }
}
