package com.veljkobogdan.quizzardapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.veljkobogdan.quizzardapp.fragments.FilesFragment;
import com.veljkobogdan.quizzardapp.fragments.HomeFragment;
import com.veljkobogdan.quizzardapp.fragments.ProfileFragment;
import com.veljkobogdan.quizzardapp.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment;
    FilesFragment filesFragment;
    ProfileFragment profileFragment;
    SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        filesFragment = new FilesFragment();
        profileFragment = new ProfileFragment();
        settingsFragment = new SettingsFragment();

        bottomNavigationView = findViewById(R.id.btmNavBar);
        loadFragment(homeFragment);

        // separate the anonymous class in a method
        bottomNavigationView.setOnItemSelectedListener(item -> {
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
                } else if (itemId == R.id.profile) {
                    loadFragment(profileFragment);
                    return true;
                } else if (itemId == R.id.add) {
                    // TODO: Add an add button, an onClick listener and popup
                    try {
                        Intent intent = new Intent(MainActivity.this, NotesListActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
            }
            catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            return false;
        });
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
}