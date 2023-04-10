package com.example.rafdnevnjak.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.rafdnevnjak.R;
import com.example.rafdnevnjak.model.MyDate;
import com.example.rafdnevnjak.viewmodels.MyDateSelectedViewModel;

public class DailyPlanFragment extends Fragment {

    private MyDateSelectedViewModel myDateSelectedViewModel;
    private TextView dateTV;

    public DailyPlanFragment(){super(R.layout.fragment_dailyplan);}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myDateSelectedViewModel = new ViewModelProvider(requireActivity()).get(MyDateSelectedViewModel.class);
        dateTV = view.findViewById(R.id.selected_date);
        System.out.println("USAO DAILYPLAN FRAGMENT");

        initObservers(view);
    }

    @SuppressLint("SetTextI18n")
    private void initObservers(View view){
        myDateSelectedViewModel.getDate().observe(getViewLifecycleOwner(), e->{
            System.out.println("Selektovan "+e.getDate());
            String month = e.getDate().getMonth().toString();
            System.out.println(e.getDutyList().get(0).getTitle());
            dateTV.setText(month.substring(0,1).toUpperCase()+month.substring(1).toLowerCase() + " "+ e.getDate().getDayOfMonth()+". "+e.getDate().getYear()+".");
        });
    }
}
