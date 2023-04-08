package com.example.rafdnevnjak.view.recycler.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.rafdnevnjak.model.MyDate;

public class MyDateDiffItemCallback extends DiffUtil.ItemCallback<MyDate> {
    @Override
    public boolean areItemsTheSame(@NonNull MyDate oldItem, @NonNull MyDate newItem) {
        return oldItem.getDate().equals(newItem.getDate());
    }

    @Override
    public boolean areContentsTheSame(@NonNull MyDate oldItem, @NonNull MyDate newItem) {
        return  oldItem.getDate().equals(newItem.getDate()) && oldItem.getHighestPriority().equals(newItem.getHighestPriority());
    }
}
