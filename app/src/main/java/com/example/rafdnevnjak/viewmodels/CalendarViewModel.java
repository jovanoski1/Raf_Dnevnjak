package com.example.rafdnevnjak.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rafdnevnjak.model.Duty;
import com.example.rafdnevnjak.model.MyDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarViewModel extends ViewModel {

    private final MutableLiveData<List<MyDate>> dates = new MutableLiveData<>();

    private List<MyDate> dateList = new ArrayList<>();
    public CalendarViewModel(){
        LocalDate ld = LocalDate.of(2023,4,1);
        for(int i=0;i<1000;i++){
            MyDate myDate = new MyDate(ld.plusDays(i));
            dateList.add(myDate);
        }

        List<MyDate> listToSubmit = new ArrayList<>(dateList);
        dates.setValue(listToSubmit);

    }

    public void addObligation(Duty duty, LocalDate ld){
        for(MyDate myDate : dateList){
            if(myDate.getDate().equals(ld)){
                myDate.getDutyList().add(duty);
                if (myDate.getHighestPriority() == null) myDate.setHighestPriority(duty.getPriority());
                else if(duty.getPriority().ordinal() > myDate.getHighestPriority().ordinal()) myDate.setHighestPriority(duty.getPriority());
                break;
            }
        }
        List<MyDate> listToSubmit = new ArrayList<>(dateList);
        dates.setValue(listToSubmit);
    }

    public MutableLiveData<List<MyDate>> getDates() {
        return dates;
    }
}
