package com.veljkobogdan.quizzardapp.ui.main;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

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
import com.veljkobogdan.quizzardapp.ui.exam.AddExamActivity;
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

        askForPermissions();

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
            } else if (item.getItemId() == R.id.calendar) {
                replaceFragment(new CalendarFragment());
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
                    Intent intent = new Intent(MainActivity.this, AddExamActivity.class);
                    startActivity(intent);
                }

                return false;
            });

            menu.show();
        });
    }

    private void askForPermissions() {
        boolean needsNotificationPermission = false;
        boolean needsExactAlarmPermission = false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            needsNotificationPermission = checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            needsExactAlarmPermission = !alarmManager.canScheduleExactAlarms();
        }

        if (needsNotificationPermission || needsExactAlarmPermission) {
            showPermissionExplanationDialog(needsNotificationPermission, needsExactAlarmPermission);
        }
    }

    private void showPermissionExplanationDialog(boolean askNotification, boolean askExactAlarm) {
        new AlertDialog.Builder(this)
                .setTitle("Permission Required")
                .setMessage("This app needs notification and alarm permissions to remind you of your events. Please allow them to ensure the app works properly.")
                .setPositiveButton("Allow", (dialog, which) -> {
                    if (askNotification && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
                    }

                    if (askExactAlarm && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Deny", null)
                .show();
    }



    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fTransaction = fm.beginTransaction();
        fTransaction.replace(binding.frameLayout.getId(), fragment);
        fTransaction.commit();

        lastLoadedFragment = fragment;
    }
}