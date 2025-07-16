package com.veljkobogdan.quizzardapp.ui.main;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Schedule;
import com.veljkobogdan.quizzardapp.data.database.entities.Subject;
import com.veljkobogdan.quizzardapp.data.models.FlashcardSetWithFlashcards;
import com.veljkobogdan.quizzardapp.data.models.ScheduleWithSubjects;
import com.veljkobogdan.quizzardapp.data.repository.ScheduleRepository;
import com.veljkobogdan.quizzardapp.ui.schedule.AddScheduleActivity;
import com.veljkobogdan.quizzardapp.ui.schedule.AddSubjectActivity;
import com.veljkobogdan.quizzardapp.util.DisplayUtil;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;


public class ScheduleFragment extends Fragment {

    private ScheduleRepository scheduleRepository;
    private List<ScheduleWithSubjects> allSchedulesWithSubjects = new ArrayList<>();
    private Schedule currentSchedule;
    private ImageButton addButton, menuButton;
    private Spinner scheduleSelectSpinner;

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

        addButton = view.findViewById(R.id.addButton);
        menuButton = view.findViewById(R.id.menuButton);
        scheduleSelectSpinner = view.findViewById(R.id.scheduleSpinner);

        addButton.setOnClickListener(v -> {
            Intent i = new Intent(requireContext(), AddSubjectActivity.class);
            i.putExtra(IntentGroup.SCHEDULE, currentSchedule);
            startActivity(i);
        });

        menuButton.setOnClickListener(v -> {
            PopupMenu menu = new PopupMenu(requireContext(), v);

            menu.getMenuInflater().inflate(R.menu.schedule_menu, menu.getMenu());
            menu.setOnMenuItemClickListener(menuItem -> {
                int id = menuItem.getItemId();
                if (id == R.id.addScheduleButton) {
                    Intent i = new Intent(requireContext(), AddScheduleActivity.class);
                    startActivity(i);
                    return true;
                } else if (id == R.id.editScheduleButton) {
                    // TODO: Add Edit schedule activity
                    return true;
                } else if (id == R.id.deleteScheduleButton) {
                    // TODO: Add a confirmation dialog to delete schedule
                    return true;
                }
                return false;
            });

            menu.show();
        });

        scheduleRepository = new ScheduleRepository(requireContext());
        loadSchedulesWithSubjects();
    }

    private void setupSchedule(List<ScheduleWithSubjects> updatedSchedulesWithSubjects) {
        clearFrameLayouts();

        for (ScheduleWithSubjects scheduleWithSubjects : updatedSchedulesWithSubjects) {
            List<Subject> subjects = scheduleWithSubjects.subjectList;

            for (Subject subject : subjects) {
                FrameLayout targetDayColumn = getDayColumn(subject.dayOfWeek);

                if (targetDayColumn == null) continue;

                MaterialCardView subjectCard = (MaterialCardView) LayoutInflater
                        .from(requireContext())
                        .inflate(R.layout.item_subject_card, targetDayColumn, false);

                int hourHeight = DisplayUtil.dpToPx(requireContext(), 60);
                int startHour = subject.startTime.getHour();
                int endHour = subject.endTime.getHour();
                int topMargin = (startHour - 6) * hourHeight;
                int height = (endHour - startHour) * hourHeight;

                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        height
                );
                params.topMargin = topMargin;
                subjectCard.setLayoutParams(params);

                TextView title = subjectCard.findViewById(R.id.subjectCardTitle);
                title.setText(subject.name);

                targetDayColumn.addView(subjectCard);
            }
        }
    }

    private void clearFrameLayouts() {
        ((FrameLayout) requireView().findViewById(R.id.day_mon)).removeAllViews();
        ((FrameLayout) requireView().findViewById(R.id.day_tue)).removeAllViews();
        ((FrameLayout) requireView().findViewById(R.id.day_wed)).removeAllViews();
        ((FrameLayout) requireView().findViewById(R.id.day_thu)).removeAllViews();
        ((FrameLayout) requireView().findViewById(R.id.day_fri)).removeAllViews();
        ((FrameLayout) requireView().findViewById(R.id.day_sat)).removeAllViews();
        ((FrameLayout) requireView().findViewById(R.id.day_sun)).removeAllViews();
    }

    public void loadSchedulesWithSubjects() {
        scheduleRepository.getAllSchedulesWithSubjects().observe(requireActivity(), updatedSchedulesWithSubjects -> {
            if (updatedSchedulesWithSubjects != null && !updatedSchedulesWithSubjects.isEmpty()) {
                allSchedulesWithSubjects = updatedSchedulesWithSubjects;

                List<String> scheduleNames = new ArrayList<>();
                for (ScheduleWithSubjects sws : updatedSchedulesWithSubjects) {
                    scheduleNames.add(sws.entry.name);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        scheduleNames
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                scheduleSelectSpinner.setAdapter(adapter);

                scheduleSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ScheduleWithSubjects selectedSchedule = allSchedulesWithSubjects.get(position);
                        setupSchedule(List.of(selectedSchedule));
                        currentSchedule = selectedSchedule.entry;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // handle deselection
                    }
                });

                // set the first one initially
                setupSchedule(List.of(allSchedulesWithSubjects.get(0)));
            }
        });

    }

    private FrameLayout getDayColumn(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return requireView().findViewById(R.id.day_mon);
            case TUESDAY:
                return requireView().findViewById(R.id.day_tue);
            case WEDNESDAY:
                return requireView().findViewById(R.id.day_wed);
            case THURSDAY:
                return requireView().findViewById(R.id.day_thu);
            case FRIDAY:
                return requireView().findViewById(R.id.day_fri);
            case SATURDAY:
                return requireView().findViewById(R.id.day_sat);
            case SUNDAY:
                return requireView().findViewById(R.id.day_sun);
            default:
                return null;
        }
    }

}