package com.example.rafdnevnjak.view.recycler.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.rafdnevnjak.model.MyDate;

public class MyDateDiffItemCallback extends DiffUtil.ItemCallback<MyDate> {
    @Override
    public boolean areItemsTheSame(@NonNull MyDate oldItem, @NonNull MyDate newItem) {
        return oldItem.getDate().equals(newItem.getDate()) && oldItem.getHighestPriority().ordinal() == newItem.getHighestPriority().ordinal();
    }
    @Override
    public boolean areContentsTheSame(@NonNull MyDate oldItem, @NonNull MyDate newItem) {
        return oldItem.getDate().equals(newItem.getDate()) && oldItem.getHighestPriority().ordinal() == newItem.getHighestPriority().ordinal();
    }
}
