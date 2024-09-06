package com.veljkobogdan.quizzardapp.listener;

import androidx.cardview.widget.CardView;

import com.veljkobogdan.quizzardapp.entity.CalendarInsert;

public interface CalendarInsertClickListener {
    void onClick(CalendarInsert calendarInsert);
    void onLongClick(CalendarInsert calendarInsert, CardView cardView);
}
