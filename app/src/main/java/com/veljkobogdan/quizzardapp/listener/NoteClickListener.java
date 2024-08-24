package com.veljkobogdan.quizzardapp.listener;

import androidx.cardview.widget.CardView;

import com.veljkobogdan.quizzardapp.entity.Note;

public interface NoteClickListener {

    void onClick(Note note);
    void onLongClick(Note note, CardView cardView);
}
