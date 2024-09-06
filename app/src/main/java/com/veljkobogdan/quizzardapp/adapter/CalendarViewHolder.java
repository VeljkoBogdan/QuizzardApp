package com.veljkobogdan.quizzardapp.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.veljkobogdan.quizzardapp.R;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView calendar_item_text;
    private final CalendarAdapter.OnItemListener onItemListener;

    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener) {
        super(itemView);

        calendar_item_text = itemView.findViewById(R.id.calendar_item_text);
        this.onItemListener = onItemListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onItemListener.onItemClick(getAdapterPosition(), calendar_item_text.getText().toString());
    }
}
