package com.veljkobogdan.quizzardapp.activity.note;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.adapter.NotesListAdapter;
import com.veljkobogdan.quizzardapp.database.RoomDB;
import com.veljkobogdan.quizzardapp.entity.Note;
import com.veljkobogdan.quizzardapp.listener.NoteClickListener;

import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity {
    RecyclerView notes_recycler;
    NotesListAdapter notesListAdapter;
    List<Note> list = new ArrayList<Note>();
    RoomDB database;
    private final NoteClickListener noteClickListener = new NoteClickListener() {
        @Override
        public void onClick(Note note) {

        }

        @Override
        public void onLongClick(Note note, CardView cardView) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_list_activity);

        notes_recycler = findViewById(R.id.notes_recycler);
        database = RoomDB.getInstance(this);
        list = database.noteDAO().getAll();

        updateRecycler(list);
    }

    private void updateRecycler(List<Note> list) {
        notes_recycler.setHasFixedSize(true);
        notes_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        notesListAdapter = new NotesListAdapter(NotesListActivity.this, list, noteClickListener);
        notes_recycler.setAdapter(notesListAdapter);
    }
}
