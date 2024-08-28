package com.veljkobogdan.quizzardapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.entity.CalendarInsert;

import java.util.List;

// DayAdapter.java
public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder> {
    private final List<CalendarInsert> calendarInserts;

    public DayAdapter(List<CalendarInsert> calendarInserts) {
        this.calendarInserts = calendarInserts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CalendarInsert calendarInsert = calendarInserts.get(position);
        holder.timeTextView.setText(calendarInsert.getTime());
        holder.descriptionTextView.setText(calendarInsert.getDescription());
    }

    @Override
    public int getItemCount() {
        return calendarInserts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView timeTextView;
        public TextView descriptionTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.time_text_view);
            descriptionTextView = itemView.findViewById(R.id.description_text_view);
        }
    }
}
