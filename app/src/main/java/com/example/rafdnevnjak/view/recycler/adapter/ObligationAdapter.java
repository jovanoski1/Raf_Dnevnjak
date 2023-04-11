package com.example.rafdnevnjak.view.recycler.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rafdnevnjak.R;
import com.example.rafdnevnjak.model.Duty;
import com.example.rafdnevnjak.model.DutyPriority;

import java.time.LocalDate;
import java.time.LocalTime;

public class ObligationAdapter extends ListAdapter<Duty, ObligationAdapter.ObligationViewHolder> {

    private ObligationItemClickListener listener;

    public ObligationAdapter(@NonNull DiffUtil.ItemCallback<Duty> diffCallback, ObligationItemClickListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ObligationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.obligation_list_item, parent, false);
        return new ObligationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObligationViewHolder holder, int position) {
        Duty duty = getItem(position);
        holder.bind(duty, listener);
    }

    public static class ObligationViewHolder extends RecyclerView.ViewHolder{

        private ImageView editIv;
        private ImageView deleteIv;

        public ObligationViewHolder(View itemView){
            super(itemView);
            editIv = itemView.findViewById(R.id.editIv);
            deleteIv = itemView.findViewById(R.id.deleteIv);

        }

        public void bind(Duty duty, final ObligationItemClickListener listener){
            if(duty.getPriority().equals(DutyPriority.LOW)) ((ImageView)itemView.findViewById(R.id.obligationPictureIv)).setBackgroundColor(Color.parseColor("#57CC99"));
            else if(duty.getPriority().equals(DutyPriority.MID)) ((ImageView)itemView.findViewById(R.id.obligationPictureIv)).setBackgroundColor(Color.parseColor("#F7CD35"));
            else ((ImageView)itemView.findViewById(R.id.obligationPictureIv)).setBackgroundColor(Color.parseColor("#F58A51"));

            if(duty.getDate().isBefore(LocalDate.now()) ||(duty.getDate().isEqual(LocalDate.now()) && duty.getStartTime().isBefore(LocalTime.now())))
                ((ConstraintLayout)itemView.findViewById(R.id.obligationItem)).setBackgroundResource(R.drawable.layout_border_past_obligation);
            ((TextView)itemView.findViewById(R.id.obligationTitleTv)).setText(duty.getTitle());
            ((TextView)itemView.findViewById(R.id.timeStartEndTv)).setText(duty.getStartTime() +" - "+duty.getEndTime());

            editIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onEditClick(duty);
                }
            });
            deleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onDeleteClick(duty);
                }
            });
        }

    }
}
