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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.rafdnevnjak.R;
import com.example.rafdnevnjak.view.recycler.adapter.DateAdapter;
import com.example.rafdnevnjak.view.recycler.differ.MyDateDiffItemCallback;
import com.example.rafdnevnjak.viewmodels.CalendarViewModel;
import com.example.rafdnevnjak.viewmodels.MyDateSelectedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CalendarFragment extends Fragment {

    private CalendarViewModel calendarViewModel;
    private MyDateSelectedViewModel myDateSelectedViewModel;

    private RecyclerView recyclerView;
    private DateAdapter dateAdapter;
    private TextView monthTextView;
    private BottomNavigationView bottomNavigationView;

    public CalendarFragment(){super(R.layout.fragment_calendar);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("USAO CALENDAR FRAGMENT");
        calendarViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);
        myDateSelectedViewModel = new ViewModelProvider(requireActivity()).get(MyDateSelectedViewModel.class);

        initView(view);
        initObservers();
        initRecycler();
        initListener();
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.listRV);
        monthTextView = view.findViewById(R.id.currentDateTV);
        bottomNavigationView = Objects.requireNonNull(getActivity()).findViewById(R.id.bottomNavigation);
    }

    private void initListener(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                int start = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                int end = gridLayoutManager.findLastCompletelyVisibleItemPosition();

                int[] niz = new int[13];
                for(int i=start;i<=end;i++){
                    LocalDate ld = LocalDate.of(2023,4,1).plusDays(i);
                    niz[ld.getMonthValue()]++;
                }
                int maxi = -1;
                int ind = -1;
                for(int i=0;i<12;i++){
                    if(niz[i]>maxi){
                        maxi=niz[i];
                        ind = i;
                    }
                }

                monthTextView.setText(LocalDate.of(2022,ind,1).getMonth()+"");
                //System.out.println("Month :"+ LocalDate.of(2022,ind,1).getMonth());
            }
        });
    }
    private void initObservers(){
        calendarViewModel.getDates().observe(getViewLifecycleOwner(), dates ->{
            dateAdapter.submitList(dates);
        });
    }


    private void initRecycler(){
        dateAdapter = new DateAdapter(new MyDateDiffItemCallback(), date->{
            myDateSelectedViewModel.getDate().setValue(date);
            bottomNavigationView.setSelectedItemId(R.id.navigation_2);
            Toast.makeText(getContext(), date.getDate().getDayOfMonth()+" "+date.getDate().getMonthValue(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
        recyclerView.setAdapter(dateAdapter);
    }
}
