package com.example.rafdnevnjak.view.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rafdnevnjak.R;
import com.example.rafdnevnjak.db.DataBaseHelper;
import com.example.rafdnevnjak.model.Duty;
import com.example.rafdnevnjak.model.DutyPriority;
import com.example.rafdnevnjak.model.MyDate;
import com.example.rafdnevnjak.utils.Utils;
import com.example.rafdnevnjak.view.activites.DetailsActivity;
import com.example.rafdnevnjak.view.activites.NewObligationActivity;
import com.example.rafdnevnjak.view.recycler.adapter.ObligationAdapter;
import com.example.rafdnevnjak.view.recycler.adapter.ObligationItemClickListener;
import com.example.rafdnevnjak.view.recycler.differ.ObligationDiffItemCallback;
import com.example.rafdnevnjak.viewmodels.CalendarViewModel;
import com.example.rafdnevnjak.viewmodels.MyDateSelectedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class DailyPlanFragment extends Fragment {

    private MyDateSelectedViewModel myDateSelectedViewModel;
    private CalendarViewModel calendarViewModel;
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
    private FloatingActionButton actionButton;
    private LocalDate date;


    public DailyPlanFragment(){super(R.layout.fragment_dailyplan);}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myDateSelectedViewModel = new ViewModelProvider(requireActivity()).get(MyDateSelectedViewModel.class);
        calendarViewModel = new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);
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
        actionButton = view.findViewById(R.id.floatingActionButton);

        initRecycler(view);
        initObservers(view);
        initListeners();
    }

    private void initRecycler(View view){
//        obligationAdapter = new ObligationAdapter(new ObligationDiffItemCallback(), obligation ->{
//            Toast.makeText(getContext(), obligation.getTitle(),Toast.LENGTH_SHORT).show();
//        });
        obligationAdapter = new ObligationAdapter(new ObligationDiffItemCallback(), new ObligationItemClickListener() {
            @Override
            public void onEditClick(Duty duty) {
                System.out.println("CLICK EDIT : " + duty.getTitle());
            }

            @Override
            public void onDeleteClick(Duty duty) {
                Snackbar.make(view,"Confirm deletition of "+duty.getTitle(), Snackbar.LENGTH_SHORT)
                                .setAction("Confirm", view -> {
                                    Utils.deleteObligationById(duty.getId(), getContext());
                                    myDateSelectedViewModel.deleteObligation(duty);
                                    calendarViewModel.removeObligation(duty);
                                }).show();
            }

            @Override
            public void onItemClick(Duty duty) {
                System.out.println("Kliknuo na ceo obligation za detalje!");
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                ArrayList<Duty> duties = (ArrayList<Duty>) myDateSelectedViewModel.getDate().getValue().getDutyList();
                intent.putExtra("obligations", duties);
                intent.putExtra("currentItem", duties.indexOf(duty));
                startActivityForResult(intent,111);
            }
        });
        recyclerView.setAdapter(obligationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("USAO ON RESULT DAILY FRAGMENT");

        if(requestCode == 111) {
            Duty deleted = (Duty) data.getSerializableExtra("deleted");

            if (deleted == null) return;
            System.out.println("-------------");
            System.out.println(myDateSelectedViewModel.getDate().getValue().getDutyList());
            System.out.println(deleted);
            System.out.println(deleted.getTitle() + " " + deleted.getId());

            Utils.deleteObligationById(deleted.getId(), getContext());
            calendarViewModel.removeObligation(deleted);
            myDateSelectedViewModel.deleteObligation(deleted);
        }
        else if (requestCode == 222){
            Duty newObligation = null;
            if (data != null) {
                newObligation = (Duty) data.getSerializableExtra("newObligation");
                if (newObligation==null) return;

                newObligation.setDate(date);

                Utils.addObligation(newObligation, (getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE)).getLong("id", 1), getActivity());
                calendarViewModel.addObligation(newObligation, newObligation.getDate());
                myDateSelectedViewModel.addObligation(newObligation);
                obligationAdapter.notifyDataSetChanged();
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private void initObservers(View view){
        myDateSelectedViewModel.getDate().observe(getViewLifecycleOwner(), e->{
            System.out.println("Selektovan "+e.getDate() + " "+e.getDutyList().size());
            String month = e.getDate().getMonth().toString();
            obligationAdapter.submitList(e.getDutyList());
            date = e.getDate();
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

        actionButton.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), NewObligationActivity.class);
            i.putExtra("date", dateTV.getText());
            startActivityForResult(i, 222);
            System.out.println("FLOATING BUTTON");
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
