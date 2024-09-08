package com.veljkobogdan.quizzardapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.veljkobogdan.quizzardapp.fragments.FilesFragment;
import com.veljkobogdan.quizzardapp.fragments.HomeFragment;
import com.veljkobogdan.quizzardapp.fragments.CalendarFragment;
import com.veljkobogdan.quizzardapp.fragments.SettingsFragment;
import com.veljkobogdan.quizzardapp.helper.RedirectHelper;

public class MainActivity extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment;
    FilesFragment filesFragment;
    CalendarFragment calendarFragment;
    SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        filesFragment = new FilesFragment();
        calendarFragment = new CalendarFragment();
        settingsFragment = new SettingsFragment();

        bottomNavigationView = findViewById(R.id.btmNavBar);
        loadFragment(homeFragment);

        // separate the anonymous class in a method
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);
    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            try {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(com.google.android.material.R.id.container, fragment);
                transaction.commit();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean onMenuItemClick(MenuItem menuItem) {
        int menuId = menuItem.getItemId();
        // TODO: add logic for the menu items
        if (menuId == R.id.new_note) {
            RedirectHelper.toNewNoteActivity(this, 0);
            return true;
        } else if (menuId == R.id.new_date) {

            return true;
        } else if (menuId == R.id.new_flashcard) {

            return true;
        } else if (menuId == R.id.new_question) {

            return true;
        } else return menuId == R.id.new_schedule;
    }

    private boolean onNavigationItemSelected(MenuItem item) {
        try {
            int itemId = item.getItemId();
            if (itemId == R.id.files) {
                loadFragment(filesFragment);
                return true;
            } else if (itemId == R.id.home) {
                loadFragment(homeFragment);
                return true;
            } else if (itemId == R.id.settings) {
                loadFragment(settingsFragment);
                return true;
            } else if (itemId == R.id.calendar) {
                loadFragment(calendarFragment);
                return true;
            } else if (itemId == R.id.add) {
                // Initializing the popup menu
                PopupMenu popupMenu = new PopupMenu(
                        MainActivity.this,
                        findViewById(R.id.add),
                        Gravity.CENTER,
                        0,
                        com.google.android.material.R.style.Widget_Material3_PopupMenu);
                // Inflating popup menu
                popupMenu.getMenuInflater().inflate(R.menu.add_file_popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(this::onMenuItemClick);
                // Showing the popup menu
                popupMenu.show();
                return true;
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return false;
    }
}