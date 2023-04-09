package com.example.rafdnevnjak.view.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rafdnevnjak.R;
import com.example.rafdnevnjak.model.MyDate;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class DateAdapter extends ListAdapter<MyDate, DateAdapter.ViewHolder> {

    private final Consumer<MyDate> onDateClicked;

    public DateAdapter(@NonNull DiffUtil.ItemCallback<MyDate> diffCallback, Consumer<MyDate> onDateClicked) {
        super(diffCallback);
        this.onDateClicked = onDateClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Pravi novi view sa mydate_layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mydate_layout, parent, false);
        return new ViewHolder(view, parent.getContext(), position ->{
            MyDate myDate = getItem(position);
            onDateClicked.accept(myDate);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyDate myDate = getItem(position);
        holder.bind(myDate);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final Context context;

        public ViewHolder(@NonNull View itemView, Context context, Consumer<Integer> onItemClicked) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(v->{
                if(getAdapterPosition() != RecyclerView.NO_POSITION){
                    onItemClicked.accept(getAdapterPosition());
                }
            });
        }


        public void bind(MyDate myDate){
            //System.out.println(myDate.getDate().getDayOfMonth());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd");
            ((TextView)itemView.findViewById(R.id.dateTV)).setText(dtf.format(myDate.getDate())+".");
        }
    }
}
