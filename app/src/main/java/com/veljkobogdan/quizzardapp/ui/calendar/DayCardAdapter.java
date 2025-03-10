package com.veljkobogdan.quizzardapp.ui.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.CalendarEntry;

import java.util.ArrayList;
import java.util.List;

/** @noinspection ClassEscapesDefinedScope*/
public class DayCardAdapter extends RecyclerView.Adapter<DayCardAdapter.DayCardViewHolder> {
    private List<CalendarEntry> calendarEntries = new ArrayList<>();

    public DayCardAdapter(Context context) {}

    public void setCalendarEntries(List<CalendarEntry> entries) {
        this.calendarEntries = entries;
        notifyItemChanged(R.id.dayCardLayout);
    }

    @NonNull
    @Override
    public DayCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_day_card, parent, false);
        return new DayCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayCardViewHolder holder, int position) {
        holder.bind(calendarEntries.get(position));
    }

    @Override
    public int getItemCount() {
        return calendarEntries.size();
    }

    public void resetEntries() {
        this.calendarEntries.clear();
        notifyItemChanged(R.id.dayCardLayout);
    }

    static class DayCardViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public DayCardViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.dayCardTitle);
        }

        public void bind(CalendarEntry entry) {
            this.title.setText(String.valueOf(entry.title));
        }
    }
}
