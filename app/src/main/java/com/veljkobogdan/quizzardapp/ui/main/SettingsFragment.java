package com.veljkobogdan.quizzardapp.ui.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.AppDatabase;
import com.veljkobogdan.quizzardapp.util.ThemeManager;

public class SettingsFragment extends Fragment {
    private Spinner themeSpinner;
    private final String[] values = new String[]{"System Theme", "Night", "Day"};
    private Button deleteAllButton;

    public SettingsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initThemeSpinner(view);

        deleteAllButton = view.findViewById(R.id.deleteAllData);
        deleteAllButton.setOnClickListener(v -> new AlertDialog.Builder(requireContext())
                .setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete all data?\nThis CAN NOT be undone!")
                .setPositiveButton("Yes", (dialog, which) -> {
                    new Thread(() -> {
                        AppDatabase db = AppDatabase.getInstance(requireContext());
                        db.tagDao().deleteAll();
                        db.noteDao().deleteAll();
                        db.noteDao().deleteAllReferences();
                        db.flashcardDao().deleteAll();
                        db.flashcardSetDao().deleteAll();
                        db.flashcardSetDao().deleteAllReferences();
                    }).start();
                    Toast.makeText(requireContext(), "Database has been cleared!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show()
        );
    }

    private void initThemeSpinner(@NonNull View view) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, values);

        themeSpinner = view.findViewById(R.id.themeSpinner);
        themeSpinner.setAdapter(adapter);
        themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int themeMode;
                switch (position) {
                    case 1:
                        themeMode = AppCompatDelegate.MODE_NIGHT_YES;
                        break;
                    case 2:
                        themeMode = AppCompatDelegate.MODE_NIGHT_NO;
                        break;
                    default:
                        themeMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
                }
                ThemeManager.setTheme(requireContext(), themeMode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        int savedTheme = ThemeManager.getCurrentTheme(requireContext());
        int selectedIndex = 0;

        if (savedTheme == AppCompatDelegate.MODE_NIGHT_YES) {
            selectedIndex = 1;
        } else if (savedTheme == AppCompatDelegate.MODE_NIGHT_NO) {
            selectedIndex = 2;
        }

        themeSpinner.setSelection(selectedIndex, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}
