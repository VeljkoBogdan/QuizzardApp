package com.veljkobogdan.quizzardapp.ui.calendar;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.card.MaterialCardView;
import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.core.DayPosition;
import com.kizitonwose.calendar.view.ViewContainer;
import com.veljkobogdan.quizzardapp.R;

import java.time.LocalDate;

public class DayViewContainer extends ViewContainer {
    public CalendarDay day;
    private TextView calendarDayText;
    private MaterialCardView calendarDayCard;
    private Context context;
    private View view;
    private LinearLayout dayCardLayout;

    public DayViewContainer(View view) {
        super(view);
        this.view = view;
        this.context = view.getContext();
        this.calendarDayText = view.findViewById(R.id.calendarDayText);
        this.calendarDayCard = view.findViewById(R.id.calendarDayCard);
        this.dayCardLayout = view.findViewById(R.id.dayCardLayout);

        view.setOnClickListener(v -> {
            // TODO: Add an on click listener to a calendar entry
        });
    }

    public void bind(CalendarDay calendarDay) {
        LocalDate date = calendarDay.getDate();
        this.calendarDayText.setText(String.valueOf(date.getDayOfMonth()));
        this.day = calendarDay;

        shadeDays();
        showDayCards();
    }

    private void showDayCards() {

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
