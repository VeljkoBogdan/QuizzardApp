package com.veljkobogdan.quizzardapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.entity.Note;
import com.veljkobogdan.quizzardapp.listener.NoteClickListener;

import java.util.ArrayList;
import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    Context context;
    List<Note> list;
    NoteClickListener listener;

    public NotesListAdapter(Context context, List<Note> list, NoteClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.notes_title.setText(list.get(position).getTitle());
        holder.notes_title.setSelected(true);

        holder.notes_text.setText(list.get(position).getText());

        holder.notes_date.setText(list.get(position).getDate());
        holder.notes_date.setSelected(true);

        if (list.get(position).isPinned()) {
            holder.notes_pin_image.setVisibility(View.VISIBLE);
            holder.notes_pin_image.setImageResource(R.drawable.pin);
        } else {
            holder.notes_pin_image.setVisibility(View.GONE);
            holder.notes_pin_image.setImageResource(0);
        }

        holder.notes_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });

        holder.notes_card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.notes_card);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class NotesViewHolder extends RecyclerView.ViewHolder {
    CardView notes_card;
    ImageView notes_pin_image;
    TextView notes_text, notes_date, notes_title;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);

        notes_card = itemView.findViewById(R.id.notes_card);
        notes_pin_image = itemView.findViewById(R.id.notes_pin_image);
        notes_text = itemView.findViewById(R.id.notes_text);
        notes_date = itemView.findViewById(R.id.notes_date);
        notes_title = itemView.findViewById(R.id.notes_title);
    }
}