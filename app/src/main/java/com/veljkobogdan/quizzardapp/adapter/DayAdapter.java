package com.veljkobogdan.quizzardapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.entity.CalendarInsert;
import com.veljkobogdan.quizzardapp.listener.CalendarInsertClickListener;

import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {
    Context context;
    List<CalendarInsert> list;
    CalendarInsertClickListener listener;

    public DayAdapter(Context context, List<CalendarInsert> list,
                      CalendarInsertClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DayViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.day_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        CalendarInsert calendarInsert = list.get(position);
        holder.timeTextView.setText(calendarInsert.getTime());
        holder.descriptionTextView.setText(calendarInsert.getDescription());

        holder.dayViewCard.setOnClickListener(view -> listener
                .onClick(list.get(holder.getAdapterPosition())));

        holder.dayViewCard.setOnLongClickListener(view -> {
            listener.onLongClick(list.get(holder.getAdapterPosition()), holder.dayViewCard);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class DayViewHolder extends RecyclerView.ViewHolder {
        public CardView dayViewCard;
        public TextView timeTextView;
        public TextView descriptionTextView;

        public DayViewHolder(View itemView) {
            super(itemView);

            timeTextView = itemView.findViewById(R.id.time_text_view);
            descriptionTextView = itemView.findViewById(R.id.description_text_view);
            dayViewCard = itemView.findViewById(R.id.day_view_card);
        }
    }
}
