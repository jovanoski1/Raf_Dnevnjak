package com.example.rafdnevnjak.view.recycler.adapter;

import com.example.rafdnevnjak.model.Duty;

public interface ObligationItemClickListener {
    void onEditClick(Duty duty);
    void onDeleteClick(Duty duty);
}
