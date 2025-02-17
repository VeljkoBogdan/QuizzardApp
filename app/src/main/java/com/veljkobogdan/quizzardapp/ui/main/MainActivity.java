package com.veljkobogdan.quizzardapp.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.databinding.ActivityMainBinding;
import com.veljkobogdan.quizzardapp.ui.sets.AddFlashcardSetActivity;
import com.veljkobogdan.quizzardapp.ui.notes.NewNoteActivity;
import com.veljkobogdan.quizzardapp.util.ThemeManager;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static Fragment lastLoadedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applyTheme(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbarIncl.toolbar;
        setSupportActionBar(toolbar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Start with the last fragment loaded, or home by default
        if (lastLoadedFragment == null) {
            replaceFragment(new HomeFragment());
        } else {
            replaceFragment(lastLoadedFragment);
        }

        // BotNav on click listener
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.files) {
                replaceFragment(new FilesFragment());
            } else if (item.getItemId() == R.id.settings) {
                replaceFragment(new SettingsFragment());
            }

            return true;
        });

        // Add button on click listener
        binding.addButton.setOnClickListener(view -> {
            PopupMenu menu = new PopupMenu(this, binding.addButton);
            menu.getMenuInflater().inflate(R.menu.main_add_popup_menu, menu.getMenu());
            menu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.addNoteButton) {
                    Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.addSetButton) {
                    Intent intent = new Intent(MainActivity.this, AddFlashcardSetActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.addExamButton) {
                    // TODO: implement exam intent
                }

                return false;
            });

            menu.show();
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fTransaction = fm.beginTransaction();
        fTransaction.replace(binding.frameLayout.getId(), fragment);
        fTransaction.commit();

        lastLoadedFragment = fragment;
    }
}