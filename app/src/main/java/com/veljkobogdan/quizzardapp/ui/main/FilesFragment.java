package com.veljkobogdan.quizzardapp.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.ui.main.files.FilesExamsFragment;
import com.veljkobogdan.quizzardapp.ui.main.files.FilesFlashcardsFragment;
import com.veljkobogdan.quizzardapp.ui.main.files.FilesNotesFragment;

public class FilesFragment extends Fragment {
    private static TabLayout tabLayout;

    public FilesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_files, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        replaceFragment(new FilesNotesFragment());

        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    replaceFragment(new FilesNotesFragment());
                } else if (tab.getPosition() == 1) {
                    replaceFragment(new FilesFlashcardsFragment());
                } else if (tab.getPosition() == 2) {
                    replaceFragment(new FilesExamsFragment());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.tabFrameLayout, fragment)
                .commit();
    }
}