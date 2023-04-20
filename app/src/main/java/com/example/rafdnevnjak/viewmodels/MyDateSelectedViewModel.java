package com.example.rafdnevnjak.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rafdnevnjak.model.Duty;
import com.example.rafdnevnjak.model.MyDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class MyDateSelectedViewModel extends ViewModel {

    private final MutableLiveData<MyDate> date = new MutableLiveData<>();

    public MyDateSelectedViewModel(){

    }

    public void deleteObligation(Duty duty){
        MyDate dateToSubmit = new MyDate(date.getValue().getDate());
        dateToSubmit.setHighestPriority(date.getValue().getHighestPriority());
        List<Duty> duties = new ArrayList<>(date.getValue().getDutyList());
        Optional<Duty> dutyObject = duties.stream().filter(duty1 -> duty1.getId().equals(duty.getId())).findFirst();
        if(dutyObject.isPresent()){
            duties.remove(dutyObject.get());
            dateToSubmit.setDutyList(duties);
            date.setValue(dateToSubmit);
        }
    }
    public void addObligation(Duty duty){
        MyDate dateToSubmit = new MyDate(date.getValue().getDate());
        List<Duty> duties = new ArrayList<>(date.getValue().getDutyList());
        if(!duties.contains(duty))duties.add(duty);
        duties.sort(new Comparator<Duty>() {
            @Override
            public int compare(Duty duty, Duty t1) {
                if (duty.getStartTime().isBefore(t1.getStartTime())) return -1;
                if (duty.getStartTime().equals(t1.getStartTime())) return 0;
                return 1;
            }
        });
        dateToSubmit.setDutyList(duties);
        dateToSubmit.updateHighestPriority();
        date.setValue(dateToSubmit);
    }

    public MutableLiveData<MyDate> getDate() {
        return date;
    }
}
