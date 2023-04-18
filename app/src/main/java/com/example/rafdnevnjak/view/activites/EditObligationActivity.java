package com.example.rafdnevnjak.view.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafdnevnjak.R;
import com.example.rafdnevnjak.model.Duty;
import com.example.rafdnevnjak.model.DutyPriority;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EditObligationActivity extends AppCompatActivity {

    private AppCompatButton saveBtn;
    private AppCompatButton cancelBtn;
    private AppCompatButton lowPriorityBtn;
    private AppCompatButton midPriorityBtn;
    private AppCompatButton highPriorityBtn;
    private TextView dateTv;
    private EditText titleEt;
    private EditText timeEt;
    private EditText descEt;

    private Duty duty;
    private List<Duty> dutyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_obligation);


        duty = (Duty) getIntent().getSerializableExtra("currentItem");
        dutyList = (List<Duty>) getIntent().getSerializableExtra("obligations");

        initView();
        initListeners();
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    private void initView(){
        saveBtn = findViewById(R.id.edit_obligation_save_btn);
        cancelBtn = findViewById(R.id.edit_obligation_cancel_btn);
        lowPriorityBtn = findViewById(R.id.editobligation_lowbtn);
        midPriorityBtn = findViewById(R.id.editobligation_midbtn);
        highPriorityBtn = findViewById(R.id.editobligation_highbtn);
        titleEt = findViewById(R.id.editobligation_title_ed);
        timeEt = findViewById(R.id.editobligation_time_ed);
        descEt = findViewById(R.id.editobligation_desc_ed);
        dateTv = findViewById(R.id.edit_obligation_date_tv);

        titleEt.setText(duty.getTitle());
        timeEt.setText(duty.getStartTime()+"-"+duty.getEndTime());
        descEt.setText(duty.getDescription());

        String month = duty.getDate().getMonth().toString();
        dateTv.setText(month.substring(0,1).toUpperCase()+month.substring(1).toLowerCase() + " "+ duty.getDate().getDayOfMonth()+". "+duty.getDate().getYear()+".");

        if (duty.getPriority().equals(DutyPriority.LOW)){
            midPriorityBtn.setBackgroundDrawable(getDrawable(R.drawable.priority_button_disabled));
            highPriorityBtn.setBackgroundDrawable(getDrawable(R.drawable.priority_button_disabled));
        }
        else if (duty.getPriority().equals(DutyPriority.MID)){
            lowPriorityBtn.setBackgroundDrawable(getDrawable(R.drawable.priority_button_disabled));
            highPriorityBtn.setBackgroundDrawable(getDrawable(R.drawable.priority_button_disabled));
        }
        else{
            midPriorityBtn.setBackgroundDrawable(getDrawable(R.drawable.priority_button_disabled));
            lowPriorityBtn.setBackgroundDrawable(getDrawable(R.drawable.priority_button_disabled));
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initListeners(){
        saveBtn.setOnClickListener(e->{
            String title = String.valueOf(titleEt.getText());
            String desc = String.valueOf(descEt.getText());
            String time = String.valueOf(timeEt.getText());
            LocalTime startTime = LocalTime.parse(time.substring(0,time.indexOf("-")));
            LocalTime endTime = LocalTime.parse(time.substring(time.indexOf("-")+1));

            duty.setTitle(title);
            duty.setDescription(desc);
            duty.setStartTime(startTime);
            duty.setEndTime(endTime);
            for (Duty d:dutyList){
                if (d.equals(duty))continue;

                if (duty.getStartTime().equals(d.getStartTime())){
                    Toast.makeText(getBaseContext(), "This time-slot already taken!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (duty.getEndTime().equals(d.getEndTime())){
                    Toast.makeText(getBaseContext(), "This time-slot already taken!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (duty.getStartTime().isAfter(d.getStartTime())&&duty.getStartTime().isBefore(d.getEndTime())){
                    Toast.makeText(getBaseContext(), "This time-slot already taken!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (duty.getEndTime().isAfter(d.getStartTime())&&duty.getStartTime().isBefore(d.getEndTime())){
                    Toast.makeText(getBaseContext(), "This time-slot already taken!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            Toast.makeText(this, "Successfully edited obligation!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent();
            i.putExtra("editedObligation", duty);
            setResult(333,i);
            finish();

        });
        cancelBtn.setOnClickListener(e->{
            finish();
        });


        lowPriorityBtn.setOnClickListener(e->{
            lowPriorityBtn.setBackgroundDrawable(getDrawable(R.drawable.priority_button_low_border));

            midPriorityBtn.setBackgroundDrawable(getDrawable(R.drawable.priority_button_disabled));
            highPriorityBtn.setBackgroundDrawable(getDrawable(R.drawable.priority_button_disabled));

            duty.setPriority(DutyPriority.LOW);
        });
        midPriorityBtn.setOnClickListener(e->{
            midPriorityBtn.setBackgroundDrawable(getDrawable(R.drawable.priority_button_mid));

            lowPriorityBtn.setBackgroundDrawable(getDrawable(R.drawable.priority_button_disabled));
            highPriorityBtn.setBackgroundDrawable(getDrawable(R.drawable.priority_button_disabled));

            duty.setPriority(DutyPriority.MID);
        });
        highPriorityBtn.setOnClickListener(e->{
            highPriorityBtn.setBackgroundDrawable(getDrawable(R.drawable.priority_button_high));

            midPriorityBtn.setBackgroundDrawable(getDrawable(R.drawable.priority_button_disabled));
            lowPriorityBtn.setBackgroundDrawable(getDrawable(R.drawable.priority_button_disabled));

            duty.setPriority(DutyPriority.HIGH);
        });
    }
}