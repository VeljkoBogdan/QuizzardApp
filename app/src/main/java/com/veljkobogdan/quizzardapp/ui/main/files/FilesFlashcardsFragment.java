package com.veljkobogdan.quizzardapp.ui.main.files;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.models.FlashcardSetWithFlashcards;
import com.veljkobogdan.quizzardapp.data.repository.FlashcardSetRepository;
import com.veljkobogdan.quizzardapp.ui.sets.FlashcardSetAdapter;
import com.veljkobogdan.quizzardapp.ui.sets.ViewFlashcardSetActivity;
import com.veljkobogdan.quizzardapp.util.IFlashcardSetLoader;

public class FilesFlashcardsFragment extends Fragment implements IFlashcardSetLoader {
    private RecyclerView recyclerView;
    private TextView noFlashcardsText;
    private FlashcardSetRepository flashcardSetRepository;
    private FlashcardSetAdapter flashcardSetAdapter;

    public FilesFlashcardsFragment() {}

    public static FilesFlashcardsFragment newInstance() {
        FilesFlashcardsFragment fragment = new FilesFlashcardsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = requireView().findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false));

        noFlashcardsText = requireView().findViewById(R.id.noFlashcardSetsText);
        flashcardSetRepository = new FlashcardSetRepository(requireContext());
        flashcardSetAdapter = new FlashcardSetAdapter(requireContext(), new FlashcardSetAdapter.OnFlashcardSetClickListener() {
            @Override
            public void onClickListener(FlashcardSetWithFlashcards flashcardSetWithFlashcards) {
                try {
                    Intent intent = new Intent(requireContext(), ViewFlashcardSetActivity.class);
                    intent.putExtra(ViewFlashcardSetActivity.FLASHCARD_SET, flashcardSetWithFlashcards);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                }
            }
        });

        recyclerView.setAdapter(flashcardSetAdapter);

        loadFlashcardSets();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_files_flashcards, container, false);
    }

    @Override
    public void loadFlashcardSets() {
        try {
            flashcardSetRepository.getFlashcardSetsWithFlashcards().observe(requireActivity(), flashcardSets -> {
                if (!flashcardSets.isEmpty()) {
                    flashcardSetAdapter.updateFlashcardSet(flashcardSets);
                    noFlashcardsText.setVisibility(View.GONE);
                } else {
                    noFlashcardsText.setVisibility(View.VISIBLE);
                }
            });
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
    }
}