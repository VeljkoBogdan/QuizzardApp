package com.veljkobogdan.quizzardapp.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Schedule;
import com.veljkobogdan.quizzardapp.data.database.entities.Subject;
import com.veljkobogdan.quizzardapp.data.models.ScheduleWithSubjects;
import com.veljkobogdan.quizzardapp.data.repository.ScheduleRepository;
import com.veljkobogdan.quizzardapp.ui.schedule.AddScheduleActivity;
import com.veljkobogdan.quizzardapp.ui.schedule.AddSubjectActivity;
import com.veljkobogdan.quizzardapp.ui.schedule.ViewSubjectActivity;
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

        scheduleSelectSpinner = view.findViewById(R.id.scheduleSpinner);

        setupAddButton(view);
        setupMenuButton(view);

        scheduleRepository = new ScheduleRepository(requireContext());
        loadSchedulesWithSubjects();
    }

    private void setupMenuButton(@NonNull View view) {
        menuButton = view.findViewById(R.id.menuButton);
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
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Delete Schedule")
                            .setMessage("Are you sure you want to delete this schedule? This action cannot be undone.")
                            .setPositiveButton("Delete", (dialog, which) -> {
                                scheduleRepository.deleteScheduleEntry(currentSchedule);
                                Toast.makeText(requireContext(), "Schedule deleted", Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                            .show();
                    return true;
                }
                return false;
            });

            menu.show();
        });
    }

    private void setupAddButton(@NonNull View view) {
        addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            Intent i = new Intent(requireContext(), AddSubjectActivity.class);
            i.putExtra(IntentGroup.SCHEDULE, currentSchedule);
            startActivity(i);
        });
    }

    private void setupSchedule(ScheduleWithSubjects updatedScheduleWithSubjects) {
        clearLayouts();

        List<Subject> subjects = updatedScheduleWithSubjects.subjectList;

        for (Subject subject : subjects) {
            ConstraintLayout targetDayColumn = getDayColumn(subject.dayOfWeek);

            if (targetDayColumn == null) continue;

            MaterialCardView subjectCard = (MaterialCardView) LayoutInflater
                    .from(requireContext())
                    .inflate(R.layout.item_subject_card, targetDayColumn, false);

            subjectCard.setOnClickListener(view -> {
                Intent i = new Intent(getContext(), ViewSubjectActivity.class);
                i.putExtra(IntentGroup.SUBJECT, subject);
                startActivity(i);
            });

            int offset = getView().findViewById(R.id.timeOffsetSpace).getHeight();
            int layoutHeight = getView().findViewById(R.id.timeColumn).getHeight() - offset;
            int hourHeight = layoutHeight / 17;
            int startMinutes = subject.startTime.getHour() * 60 + subject.startTime.getMinute();
            int endMinutes = subject.endTime.getHour() * 60 + subject.endTime.getMinute();
            int baseMinutes = 6 * 60; // 06:00

            int top = (startMinutes - baseMinutes) * hourHeight / 60;
            int height = (endMinutes - startMinutes) * hourHeight / 60;

            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    height
            );
            params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            params.topMargin = top;
            subjectCard.setLayoutParams(params);

            TextView title = subjectCard.findViewById(R.id.subjectCardTitle);
            TextView time = subjectCard.findViewById(R.id.subjectCardTime);
            title.setText(subject.name);
            time.setText(subject.startTime.toString() + " - " + subject.endTime.toString());

            int contrastingColor = getContrastingTextColor(subject.color);
            title.setTextColor(contrastingColor);
            time.setTextColor(contrastingColor);
            subjectCard.setCardBackgroundColor(subject.color);

            targetDayColumn.addView(subjectCard);
        }
    }

    private void clearLayouts() {
        ((ConstraintLayout) requireView().findViewById(R.id.day_mon)).removeAllViews();
        ((ConstraintLayout) requireView().findViewById(R.id.day_tue)).removeAllViews();
        ((ConstraintLayout) requireView().findViewById(R.id.day_wed)).removeAllViews();
        ((ConstraintLayout) requireView().findViewById(R.id.day_thu)).removeAllViews();
        ((ConstraintLayout) requireView().findViewById(R.id.day_fri)).removeAllViews();
        ((ConstraintLayout) requireView().findViewById(R.id.day_sat)).removeAllViews();
        ((ConstraintLayout) requireView().findViewById(R.id.day_sun)).removeAllViews();
    }

    public void loadSchedulesWithSubjects() {
        scheduleRepository.getAllSchedulesWithSubjects().observe(requireActivity(), updatedSchedulesWithSubjects -> {
            if (!isAdded() || getContext() == null || getView() == null) return; // wait for ui to assemble
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
                        setupSchedule(selectedSchedule);
                        currentSchedule = selectedSchedule.entry;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // handle deselection
                    }
                });

                // set the first one initially
                setupSchedule(allSchedulesWithSubjects.get(0));
            }
        });

    }

    private ConstraintLayout getDayColumn(DayOfWeek dayOfWeek) {
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

    public int getContrastingTextColor(int color) {
        double darkness = 1 - (
                0.299 * Color.red(color) +
                        0.587 * Color.green(color) +
                        0.114 * Color.blue(color)
        ) / 255;

        return (darkness >= 0.5) ? Color.WHITE : Color.BLACK;
    }
}