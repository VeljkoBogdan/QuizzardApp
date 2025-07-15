package com.veljkobogdan.quizzardapp.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.util.DottedLineBackgroundDrawable;


public class ScheduleFragment extends Fragment {

    private RecyclerView subjectRecycler;

    public ScheduleFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        subjectRecycler = view.findViewById(R.id.subjectRecycler);
        DottedLineBackgroundDrawable backgroundDrawable = new DottedLineBackgroundDrawable();
        subjectRecycler.setBackground(backgroundDrawable);
    }
}