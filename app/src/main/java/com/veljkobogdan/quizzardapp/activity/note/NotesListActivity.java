package com.veljkobogdan.quizzardapp.activity.note;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.adapter.NotesListAdapter;
import com.veljkobogdan.quizzardapp.database.RoomDB;
import com.veljkobogdan.quizzardapp.entity.Note;
import com.veljkobogdan.quizzardapp.helper.RedirectHelper;
import com.veljkobogdan.quizzardapp.listener.NoteClickListener;

import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity {
    RecyclerView notes_recycler;
    NotesListAdapter notesListAdapter;
    List<Note> list = new ArrayList<>();
    RoomDB database;
    TextView title;
    ImageButton image_back, image_add;

    private final NoteClickListener noteClickListener = new NoteClickListener() {
        @Override
        public void onClick(Note note) {
            Intent intent = new Intent(NotesListActivity.this, NoteActivity.class);
            // send Note data to NoteActivity
            intent.putExtra("note", note);
            startActivity(intent);
        }

        @Override
        public void onLongClick(Note note, CardView cardView) {
            PopupMenu popupMenu = new PopupMenu(
                    NotesListActivity.this,
                    new View(cardView.getContext()),
                    Gravity.CENTER,
                    0,
                    com.google.android.material.R.style.Widget_Material3_PopupMenu);
            // Inflating popup menu
            popupMenu.getMenuInflater().inflate(R.menu.note_options_popup, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> onMenuItemClick(note, menuItem));
            // Showing the popup menu
            MenuItem item = popupMenu.getMenu().findItem(R.id.pin);
            if (note.isPinned){
                item.setTitle("Unpin");
            } else {
                item.setTitle("Pin");
            }
            popupMenu.show();
        }

        private boolean onMenuItemClick(Note note, MenuItem menuItem) {
            int menuId = menuItem.getItemId();
            if (menuId == R.id.delete) {
                database.noteDAO().delete(note);
                updateRecycler(database.noteDAO().getAll());
                return true;
            } else if (menuId == R.id.edit) {
                // go to EditNoteActivity
                Intent intent = new Intent(NotesListActivity.this, EditNoteActivity.class);
                // send Note data to EditNoteActivity
                intent.putExtra("note", note);
                startActivity(intent);
                return true;
            } else if (menuId == R.id.pin) {
                // set isPinned of the note to true
                if (note.isPinned()) {
                    note.setPinned(false);
                    database.noteDAO().update(note.getId(), note.getTitle(), note.getText(), note.getDate(), note.isPinned());
                } else {
                    note.setPinned(true);
                    database.noteDAO().update(note.getId(), note.getTitle(), note.getText(), note.getDate(), note.isPinned());
                }
                updateRecycler(database.noteDAO().getAll());
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_list_activity);

        image_back = findViewById(R.id.image_back);
        image_add = findViewById(R.id.image_add);
        title = findViewById(R.id.title);
        title.setText("Your notes");

        image_back.setOnClickListener(v -> {

            RedirectHelper.toMainActivity(NotesListActivity.this, 0);
        });
        image_add.setOnClickListener(v -> {
            RedirectHelper.toNewNoteActivity(NotesListActivity.this, 0);
        });

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
