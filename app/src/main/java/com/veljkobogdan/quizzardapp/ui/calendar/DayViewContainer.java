package com.veljkobogdan.quizzardapp.ui.calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.core.DayPosition;
import com.kizitonwose.calendar.view.ViewContainer;
import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.repository.CalendarEntryRepository;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

import java.time.LocalDate;

public class DayViewContainer extends ViewContainer {
    private final Activity activity;
    public CalendarDay day;
    private final TextView calendarDayText;
    private final MaterialCardView calendarDayCard;
    private final Context context;
    private final View view;
    private final RecyclerView dayCardLayout;
    private final DayCardAdapter dayCardAdapter;
    private final CalendarEntryRepository entryRepository;

    public DayViewContainer(View view, Activity activity) {
        super(view);
        this.view = view;
        this.activity = activity;
        this.context = view.getContext();
        this.calendarDayText = view.findViewById(R.id.calendarDayText);
        this.calendarDayCard = view.findViewById(R.id.calendarDayCard);
        this.entryRepository = new CalendarEntryRepository(view.getContext());

        view.setOnClickListener(v -> {
            Intent intent = new Intent(this.activity, DayViewActivity.class);
            intent.putExtra(IntentGroup.DAY, this.day);
            activity.startActivity(intent);
        });

        dayCardAdapter = new DayCardAdapter(view.getContext());

        dayCardLayout = view.findViewById(R.id.dayCardLayout);
        dayCardLayout.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        dayCardLayout.setAdapter(dayCardAdapter);
    }

    private void loadEntries() {
        if (!(activity instanceof LifecycleOwner)) return;
        entryRepository.getCalendarEntriesForDay(this.day).observe((LifecycleOwner) activity, entries -> {
            if (!entries.isEmpty()) {
                dayCardAdapter.setCalendarEntries(entries);
            }
        });
    }

    public void bind(CalendarDay calendarDay) {
        LocalDate date = calendarDay.getDate();
        this.calendarDayText.setText(String.valueOf(date.getDayOfMonth()));
        this.day = calendarDay;

        clearEntries();
        shadeDays();
        loadEntries();
    }

    private void clearEntries() {
        dayCardAdapter.resetEntries();
    }

    public void shadeDays() {
        LocalDate date = day.getDate();

        // Reset color
        this.calendarDayText.setTextColor(context.getResources()
                .getColor(R.color.md_theme_onSurface, context.getTheme()));
        this.calendarDayCard.setStrokeColor(context.getResources()
                .getColor(R.color.md_theme_surfaceVariant, context.getTheme()));

        // Get out and in dates, and dim their cards
        if (day.getPosition() == DayPosition.MonthDate) {
            this.calendarDayText.setTextColor(context.getResources()
                    .getColor(R.color.md_theme_onSurface, context.getTheme()));
        } else {
            this.calendarDayText.setTextColor(context.getResources()
                    .getColor(R.color.md_theme_surfaceContainerHigh, context.getTheme()));
            this.calendarDayCard.setStrokeColor(context.getResources()
                    .getColor(R.color.md_theme_surfaceContainerHigh, context.getTheme()));
        }

        // Highlight the current day
        if (date.isEqual(LocalDate.now())) {
            this.calendarDayText.setTextColor(context.getResources()
                    .getColor(R.color.md_theme_onSurface, context.getTheme()));
            this.calendarDayCard.setStrokeColor(context.getResources()
                    .getColor(R.color.md_theme_onSurface, context.getTheme()));
        }
    }
}
