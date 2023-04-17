package com.example.rafdnevnjak.view.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.rafdnevnjak.R;
import com.example.rafdnevnjak.model.Duty;
import com.example.rafdnevnjak.utils.Utils;
import com.example.rafdnevnjak.view.activites.EditObligationActivity;
import com.example.rafdnevnjak.viewmodels.CalendarViewModel;
import com.example.rafdnevnjak.viewmodels.MyDateSelectedViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ObligationDetailsFragment extends Fragment {

    private TextView titleTv;
    private TextView dateTv;
    private TextView descTv;
    private TextView timeTv;
    private AppCompatButton editBtn;
    private AppCompatButton deleteBtn;
    private Duty duty;
    private List<Duty> dutyList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_detail_obligation, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==3352 && data!=null){
            Duty editedDuty = (Duty) data.getSerializableExtra("editedObligation");

            Intent i = new Intent();
            i.putExtra("editedObligation", editedDuty);
            getActivity().setResult(333, i);
            getActivity().finish();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        duty = ((Duty)args.getSerializable("object"));
        dutyList = (List<Duty>) args.getSerializable("obligations");
        //System.out.println(((Duty)args.getSerializable("object")).getTitle());
        System.out.println(dutyList);
        initView(view);


        editBtn.setOnClickListener(e->{
            Intent i = new Intent(getActivity(), EditObligationActivity.class);
            i.putExtra("currentItem", duty);
            i.putExtra("obligations", (Serializable) dutyList);
            startActivityForResult(i, 3352);
        });

        deleteBtn.setOnClickListener(e->{
            Snackbar.make(view,"Confirm deletition of "+duty.getTitle(), Snackbar.LENGTH_SHORT)
                    .setAction("Confirm", d -> {
                        Intent i = new Intent();
                        i.putExtra("deleted", duty);
                        getActivity().setResult(111, i);
                        getActivity().finish();
                    }).show();

        });
    }

    private void initView(View view){
        titleTv = view.findViewById(R.id.detail_title);
        dateTv = view.findViewById(R.id.details_obligation_date);
        timeTv = view.findViewById(R.id.time_details_tv);
        descTv = view.findViewById(R.id.description_details_tv);
        editBtn = view.findViewById(R.id.edit_details_btn);
        deleteBtn = view.findViewById(R.id.delete_details_btn);

        titleTv.setText(duty.getTitle());
        String month = duty.getDate().getMonth().toString();
        dateTv.setText( month.substring(0,1).toUpperCase()+month.substring(1).toLowerCase()+ " " + duty.getDate().getDayOfMonth() +". "+duty.getDate().getYear()+".");
        timeTv.setText(duty.getStartTime() +" - "+duty.getEndTime());
        descTv.setText(duty.getDescription());
    }
}


