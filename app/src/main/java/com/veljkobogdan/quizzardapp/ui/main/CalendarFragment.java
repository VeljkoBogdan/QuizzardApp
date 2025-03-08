package com.veljkobogdan.quizzardapp.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.card.MaterialCardView;
import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.core.CalendarMonth;
import com.kizitonwose.calendar.core.DayPosition;
import com.kizitonwose.calendar.view.CalendarView;
import com.kizitonwose.calendar.view.MonthDayBinder;
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder;
import com.kizitonwose.calendar.view.ViewContainer;
import com.veljkobogdan.quizzardapp.R;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

public class CalendarFragment extends Fragment {
    private CalendarView calendarView;

    public CalendarFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarView = requireView().findViewById(R.id.calendarView);
        calendarView.setDayBinder(new MonthDayBinder<DayViewContainer>() {
            @Override
            public void bind(@NonNull DayViewContainer container, CalendarDay calendarDay) {
                LocalDate date = calendarDay.getDate();

                // Set the text of the view
                container.calendarDayText.setText(String.valueOf(date.getDayOfMonth()));

                // Set the day of the view
                container.day = calendarDay;

                // Reset color
                container.calendarDayText.setTextColor(getResources()
                        .getColor(R.color.md_theme_onSurface, requireActivity().getTheme()));
                container.calendarDayCard.setStrokeColor(getResources()
                        .getColor(R.color.md_theme_surfaceVariant, requireActivity().getTheme()));

                // Get out and in dates, and dim their cards
                if (calendarDay.getPosition() == DayPosition.MonthDate) {
                    container.calendarDayText.setTextColor(getResources()
                            .getColor(R.color.md_theme_onSurface, requireActivity().getTheme()));
                } else {
                    container.calendarDayText.setTextColor(getResources()
                            .getColor(R.color.md_theme_surfaceContainerHigh, requireActivity().getTheme()));
                    container.calendarDayCard.setStrokeColor(getResources()
                            .getColor(R.color.md_theme_surfaceContainerHigh, requireActivity().getTheme()));
                }

                // Highlight the current day
                if (date.isEqual(LocalDate.now())) {
                    container.calendarDayText.setTextColor(getResources()
                            .getColor(R.color.md_theme_onSurface, requireActivity().getTheme()));
                    container.calendarDayCard.setStrokeColor(getResources()
                            .getColor(R.color.md_theme_onSurface, requireActivity().getTheme()));
                }
            }

            @NonNull
            @Override
            public DayViewContainer create(@NonNull View view) {
                return new DayViewContainer(view);
            }

        });

        calendarView.setMonthHeaderBinder(new MonthHeaderFooterBinder<MonthHeaderContainer>() {
            @Override
            public void bind(@NonNull MonthHeaderContainer container, CalendarMonth calendarMonth) {
                container.calendarMonthText
                        .setText(String.valueOf(calendarMonth.getYearMonth().getMonth()));
            }

            @NonNull
            @Override
            public MonthHeaderContainer create(@NonNull View view) {
                return new MonthHeaderContainer(view);
            }
        });

        YearMonth currentMonth = YearMonth.now();
        YearMonth startMonth = currentMonth.minusMonths(12 * 10);
        YearMonth endMonth = currentMonth.plusMonths(12 * 10);
        DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY;
        calendarView.setup(startMonth, endMonth, firstDayOfWeek);
        calendarView.scrollToMonth(currentMonth);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    static class DayViewContainer extends ViewContainer {
        public CalendarDay day;
        TextView calendarDayText;
        MaterialCardView calendarDayCard;

        public DayViewContainer(View view) {
            super(view);
            calendarDayText = view.findViewById(R.id.calendarDayText);
            calendarDayCard = view.findViewById(R.id.calendarDayCard);
            view.setOnClickListener(v -> {
                // TODO: Add an on click listener to a calendar entry
            });
        }
    }

    static class MonthHeaderContainer extends ViewContainer {
        TextView calendarMonthText;
        FlexboxLayout monthGrid;

        public MonthHeaderContainer(@NonNull View view) {
            super(view);
            calendarMonthText = view.findViewById(R.id.calendarMonthHeaderText);
            monthGrid = view.findViewById(R.id.monthGrid);
        }
    }
}