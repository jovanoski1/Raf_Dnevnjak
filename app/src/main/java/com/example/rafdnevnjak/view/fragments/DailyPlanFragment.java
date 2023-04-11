package com.example.rafdnevnjak.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rafdnevnjak.R;
import com.example.rafdnevnjak.model.MyDate;
import com.example.rafdnevnjak.view.recycler.adapter.ObligationAdapter;
import com.example.rafdnevnjak.view.recycler.differ.ObligationDiffItemCallback;
import com.example.rafdnevnjak.viewmodels.MyDateSelectedViewModel;

public class DailyPlanFragment extends Fragment {

    private MyDateSelectedViewModel myDateSelectedViewModel;
    private TextView dateTV;
    private RecyclerView recyclerView;
    private ObligationAdapter obligationAdapter;

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
        recyclerView = view.findViewById(R.id.obligationRv);
        System.out.println("USAO DAILYPLAN FRAGMENT");

        initRecycler();
        initObservers(view);
    }

    private void initRecycler(){
        obligationAdapter = new ObligationAdapter(new ObligationDiffItemCallback(), obligation ->{
            Toast.makeText(getContext(), obligation.getTitle(),Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(obligationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @SuppressLint("SetTextI18n")
    private void initObservers(View view){
        myDateSelectedViewModel.getDate().observe(getViewLifecycleOwner(), e->{
            System.out.println("Selektovan "+e.getDate());
            String month = e.getDate().getMonth().toString();
            System.out.println(e.getDutyList().get(0).getTitle());
            obligationAdapter.submitList(e.getDutyList());
            dateTV.setText(month.substring(0,1).toUpperCase()+month.substring(1).toLowerCase() + " "+ e.getDate().getDayOfMonth()+". "+e.getDate().getYear()+".");
        });
    }
}
