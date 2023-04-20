package com.example.rafdnevnjak.view.recycler.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.rafdnevnjak.model.Duty;

public class ObligationDiffItemCallback extends DiffUtil.ItemCallback<Duty> {
    @Override
    public boolean areItemsTheSame(@NonNull Duty oldItem, @NonNull Duty newItem) {
        return oldItem.getId().equals(newItem.getId()) && oldItem.getStartTime().equals(newItem.getStartTime()) && oldItem.getEndTime().equals(newItem.getEndTime())
                && oldItem.getTitle().equals(newItem.getTitle());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Duty oldItem, @NonNull Duty newItem) {
        return oldItem.getId().equals(newItem.getId()) && oldItem.getStartTime().equals(newItem.getStartTime()) && oldItem.getEndTime().equals(newItem.getEndTime())
                && oldItem.getTitle().equals(newItem.getTitle());
    }
}
