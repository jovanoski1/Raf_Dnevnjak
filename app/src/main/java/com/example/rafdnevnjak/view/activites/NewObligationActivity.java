package com.example.rafdnevnjak.view.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rafdnevnjak.R;
import com.example.rafdnevnjak.model.Duty;
import com.example.rafdnevnjak.model.DutyPriority;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class NewObligationActivity extends AppCompatActivity {

    private AppCompatButton createBtn;
    private AppCompatButton cancelBtn;
    private AppCompatButton lowPriorityBtn;
    private AppCompatButton midPriorityBtn;
    private AppCompatButton highPriorityBtn;
    private TextView dateTv;
    private EditText titleEt;
    private EditText timeEt;
    private EditText descEt;
    private DutyPriority dutyPriority = DutyPriority.NO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_obligation);

        initView();
        initListeners();
        dateTv.setText(getIntent().getStringExtra("date"));
        System.out.println(getIntent().getSerializableExtra("obligations"));
    }

    private void initView(){
        createBtn = findViewById(R.id.new_obligation_create_btn);
        cancelBtn = findViewById(R.id.new_obligation_cancel_btn);
        lowPriorityBtn = findViewById(R.id.newobligation_lowbtn);
        midPriorityBtn = findViewById(R.id.newobligation_midbtn);
        highPriorityBtn = findViewById(R.id.newobligation_highbtn);
        titleEt = findViewById(R.id.newobligation_title_ed);
        timeEt = findViewById(R.id.newobligation_time_ed);
        descEt = findViewById(R.id.newobligation_desc_ed);
        dateTv = findViewById(R.id.new_obligation_date_tv);
    }

    private void initListeners(){
        createBtn.setOnClickListener(e->{
            String title = String.valueOf(titleEt.getText());
            String desc = String.valueOf(descEt.getText());
            String time = String.valueOf(timeEt.getText());
            LocalTime startTime = LocalTime.parse(time.substring(0,time.indexOf("-")));
            LocalTime endTime = LocalTime.parse(time.substring(time.indexOf("-")+1));
//            System.out.println(title + " " +desc);
//            System.out.println("startTime: " + startTime);
//            System.out.println("endTime: "+endTime);

            if (dutyPriority.equals(DutyPriority.NO)) return;
            Duty duty = new Duty();
            duty.setTitle(title);
            duty.setDescription(desc);
            duty.setStartTime(startTime);
            duty.setEndTime(endTime);
            duty.setPriority(dutyPriority);

            Intent i = new Intent();
            i.putExtra("newObligation", duty);
            setResult(222, i);
            finish();
        });
        cancelBtn.setOnClickListener(view -> {
            finish();
        });
        lowPriorityBtn.setOnClickListener(e->{
            dutyPriority = DutyPriority.LOW;
        });
        midPriorityBtn.setOnClickListener(e->{
            dutyPriority = DutyPriority.MID;
        });
        highPriorityBtn.setOnClickListener(e->{
            dutyPriority = DutyPriority.HIGH;
        });
    }
}