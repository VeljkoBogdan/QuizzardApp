package com.veljkobogdan.quizzardapp.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.activity.calendar.CalendarDayView;
import com.veljkobogdan.quizzardapp.adapter.CalendarAdapter;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment implements CalendarAdapter.OnItemListener {
    ImageButton calendar_previous, calendar_next;
    TextView calendar_date;
    RecyclerView calendar_recycler_view;
    LocalDate selectedDate;

    public CalendarFragment() {
        // Required empty public constructor
    }

    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        initWidgets(view);
        selectedDate = LocalDate.now();
        setMonthView(view);

        return view;
    }

    @SuppressLint("NewApi")
    private void setMonthView(View view) {
        try {
            calendar_date.setText(getStringFromDate(selectedDate));
            ArrayList<String> daysInMonth = getDaysInMonth(selectedDate);
            CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(
                    calendar_recycler_view.getContext(),
                    7);
            calendar_recycler_view.setLayoutManager(layoutManager);
            calendar_recycler_view.setAdapter(calendarAdapter);
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("NewApi")
    private ArrayList<String> getDaysInMonth(LocalDate selectedDate) {
        try {
            ArrayList<String> daysInMonth = new ArrayList<>();
            YearMonth yearMonth = YearMonth.from(selectedDate);
            int daysInMonthInt = yearMonth.lengthOfMonth();
            LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
            int dayOfWeekInt = firstOfMonth.getDayOfWeek().getValue();

            for (int i = 1; i <= 42; i++) {
                if (i <= dayOfWeekInt || i > daysInMonthInt + dayOfWeekInt) {
                    daysInMonth.add("");
                } else {
                    daysInMonth.add(String.valueOf(i - dayOfWeekInt));
                }
            }
            return daysInMonth;
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            return new ArrayList<>();
        }
    }

    @SuppressLint("NewApi")
    private String getStringFromDate(LocalDate date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
            return date.format(formatter);
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            return "";
        }
    }

    @SuppressLint("NewApi")
    private void initWidgets(View view) {
        try {
            calendar_recycler_view = view.findViewById(R.id.calendar_recycler_view);

            calendar_next = view.findViewById(R.id.calendar_next);
            calendar_previous = view.findViewById(R.id.calendar_previous);
            calendar_date = view.findViewById(R.id.calendar_date);

            calendar_next.setOnClickListener(v -> onNextClick(v));
            calendar_previous.setOnClickListener(v -> onPreviousClick(v));
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("NewApi")
    private void onNextClick(View v) {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView(v);
    }

    @SuppressLint("NewApi")
    private void onPreviousClick(View v) {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView(v);

    }

    @SuppressLint("NewApi")
    @Override
    public void onItemClick(int position, String dayText) {
        if (dayText.isEmpty()) {
        } else {
            try {
                Intent intent = new Intent(getActivity(), CalendarDayView.class);
                intent.putExtra("date", dayText + " " + getStringFromDate(selectedDate));
                startActivity(intent);
            } catch (Exception e) {
                Log.e("RUNTIME_ERROR", e.getMessage());
            }
        }
    }
}