package com.example.rafdnevnjak.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rafdnevnjak.R;
import com.example.rafdnevnjak.model.DutyPriority;
import com.example.rafdnevnjak.model.MyDate;
import com.example.rafdnevnjak.view.recycler.adapter.ObligationAdapter;
import com.example.rafdnevnjak.view.recycler.differ.ObligationDiffItemCallback;
import com.example.rafdnevnjak.viewmodels.MyDateSelectedViewModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Collectors;

public class DailyPlanFragment extends Fragment {

    private MyDateSelectedViewModel myDateSelectedViewModel;
    private TextView dateTV;
    private RecyclerView recyclerView;
    private ObligationAdapter obligationAdapter;

    private AppCompatButton lowPriorityBtn;
    private int lowPriorityBtnClickCnt;

    private AppCompatButton midPriorityBtn;
    private int midPriorityBtnClickCnt;

    private AppCompatButton highPriorityBtn;
    private int highPriorityBtnClickCnt;

    private EditText filterByTitleEt;

    private CheckBox pastObligationsCb;


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
        lowPriorityBtn = view.findViewById(R.id.filterLowPriorityBtn);
        midPriorityBtn = view.findViewById(R.id.filterMidPriorityBtn);
        highPriorityBtn = view.findViewById(R.id.filterHighPriorityBtn);
        highPriorityBtnClickCnt=0;
        midPriorityBtnClickCnt=0;
        lowPriorityBtnClickCnt=0;

        filterByTitleEt = view.findViewById(R.id.filerByTitleEt);
        pastObligationsCb = view.findViewById(R.id.pastObligationsCb);

        initRecycler();
        initObservers(view);
        initListeners();
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
            obligationAdapter.submitList(e.getDutyList());
            dateTV.setText(month.substring(0,1).toUpperCase()+month.substring(1).toLowerCase() + " "+ e.getDate().getDayOfMonth()+". "+e.getDate().getYear()+".");
        });
    }

    private void initListeners(){
        lowPriorityBtn.setOnClickListener(e->{
            if(lowPriorityBtnClickCnt%2==0)obligationAdapter.submitList(myDateSelectedViewModel.getDate().getValue().getDutyList().stream().filter(duty ->  duty.getPriority().equals(DutyPriority.LOW)).collect(Collectors.toList()));
            else obligationAdapter.submitList(myDateSelectedViewModel.getDate().getValue().getDutyList());
            lowPriorityBtnClickCnt++;
        });
        midPriorityBtn.setOnClickListener(e->{
            if(midPriorityBtnClickCnt%2==0)obligationAdapter.submitList(myDateSelectedViewModel.getDate().getValue().getDutyList().stream().filter(duty ->  duty.getPriority().equals(DutyPriority.MID)).collect(Collectors.toList()));
            else obligationAdapter.submitList(myDateSelectedViewModel.getDate().getValue().getDutyList());
            midPriorityBtnClickCnt++;
        });
        highPriorityBtn.setOnClickListener(e->{
            if(highPriorityBtnClickCnt%2==0) obligationAdapter.submitList(myDateSelectedViewModel.getDate().getValue().getDutyList().stream().filter(duty ->  duty.getPriority().equals(DutyPriority.HIGH)).collect(Collectors.toList()));
            else obligationAdapter.submitList(myDateSelectedViewModel.getDate().getValue().getDutyList());
            highPriorityBtnClickCnt++;
        });

        filterByTitleEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                obligationAdapter.submitList(myDateSelectedViewModel.getDate().getValue().getDutyList().stream().filter(duty -> duty.getTitle().startsWith(editable.toString())).collect(Collectors.toList()));
            }
        });

        pastObligationsCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    obligationAdapter.submitList(myDateSelectedViewModel.getDate().getValue().getDutyList());
                }
                else{
                    obligationAdapter.submitList(myDateSelectedViewModel.getDate().getValue().getDutyList().stream().filter(duty -> duty.getDate().isAfter(LocalDate.now()) || (duty.getDate().isEqual(LocalDate.now())&& duty.getStartTime().isAfter(LocalTime.now()))).collect(Collectors.toList()));

                }
            }
        });
    }
}
