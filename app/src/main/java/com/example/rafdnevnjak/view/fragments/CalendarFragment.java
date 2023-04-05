package com.example.rafdnevnjak.view.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.rafdnevnjak.R;
import com.example.rafdnevnjak.viewmodels.CalendarViewModel;

public class CalendarFragment extends Fragment {

    private CalendarViewModel calendarViewModel;

    public CalendarFragment(){super(R.layout.fragment_calendar);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);

        initObservers();
    }

    private void initObservers(){
        calendarViewModel.getmText().observe(getViewLifecycleOwner(), System.out::println);
    }
}
