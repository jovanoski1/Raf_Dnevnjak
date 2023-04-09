package com.example.rafdnevnjak.view.fragments;

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

    private TextView textView;
    private MyDateSelectedViewModel myDateSelectedViewModel;

    public DailyPlanFragment(){super(R.layout.fragment_dailyplan);}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myDateSelectedViewModel = new ViewModelProvider(requireActivity()).get(MyDateSelectedViewModel.class);
        textView = view.findViewById(R.id.prokic);
        System.out.println("USAO DAILYPLAN FRAGMENT");

        initObservers(view);
    }

    private void initObservers(View view){
        myDateSelectedViewModel.getDate().observe(getViewLifecycleOwner(), e->{
            System.out.println("Selektovan "+e.getDate());
            textView.setText(e.getDate()+"");
        });
    }
}
