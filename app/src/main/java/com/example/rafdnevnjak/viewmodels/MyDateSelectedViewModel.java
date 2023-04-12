package com.example.rafdnevnjak.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rafdnevnjak.model.Duty;
import com.example.rafdnevnjak.model.MyDate;

import java.util.ArrayList;
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

    public MutableLiveData<MyDate> getDate() {
        return date;
    }
}
