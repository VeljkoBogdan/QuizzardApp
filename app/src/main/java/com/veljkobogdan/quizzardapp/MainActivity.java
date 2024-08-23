package com.veljkobogdan.quizzardapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.SurfaceControl;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Toast.makeText(this, "tried", Toast.LENGTH_SHORT).show();
            try {
                int itemId = item.getItemId();
                if (itemId == R.id.files) {
                    loadFragment(filesFragment);
                    Toast.makeText(this, "files", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.home) {
                    loadFragment(homeFragment);
                    Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.settings) {
                    loadFragment(settingsFragment);
                    Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.profile) {
                    loadFragment(profileFragment);
                    Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
            catch (Exception e) {
                throw e;
            }

            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(com.google.android.material.R.id.container, fragment);
            transaction.commit();
        }
    }
}