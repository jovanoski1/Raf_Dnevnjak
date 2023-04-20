package com.example.rafdnevnjak.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rafdnevnjak.model.Duty;
import com.example.rafdnevnjak.model.MyDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CalendarViewModel extends ViewModel {

    private final MutableLiveData<List<MyDate>> dates = new MutableLiveData<>();

    private List<MyDate> dateList = new ArrayList<>();
    public CalendarViewModel(){
        LocalDate ld = LocalDate.of(2022,12,26);
        for(int i=0;i<1000;i++){
            MyDate myDate = new MyDate(ld.plusDays(i));
            dateList.add(myDate);
        }

        List<MyDate> listToSubmit = new ArrayList<>(dateList);
        dates.setValue(listToSubmit);

    }

    public void updateObligation(Duty duty){
        for (MyDate myDate: dateList){
            boolean found = false;
            if (myDate.getDate().equals(duty.getDate())){
                for (Duty d:myDate.getDutyList()){
                    if (d.equals(duty)){
                        d.setEndTime(duty.getEndTime());
                        d.setDescription(duty.getDescription());
                        d.setStartTime(duty.getStartTime());
                        d.setTitle(duty.getTitle());
                        d.setPriority(duty.getPriority());
                        found = true;
                        myDate.updateHighestPriority();
                        break;
                    }
                }
            }
            if (found)break;
        }
        List<MyDate> listToSubmit = new ArrayList<>(dateList);
        dates.setValue(listToSubmit);
    }

    public void addObligation(Duty duty, LocalDate ld){
        for(MyDate myDate : dateList){
            if(myDate.getDate().equals(ld) && !myDate.getDutyList().contains(duty)){
                System.out.println("dodao");
                myDate.getDutyList().add(duty);
                myDate.getDutyList().sort(new Comparator<Duty>() {
                    @Override
                    public int compare(Duty duty, Duty t1) {
                        if (duty.getStartTime().isBefore(t1.getStartTime())) return -1;
                        if (duty.getStartTime().equals(t1.getStartTime())) return 0;
                        return 1;
                    }
                });
                if (myDate.getHighestPriority() == null) myDate.setHighestPriority(duty.getPriority());
                else if(duty.getPriority().ordinal() > myDate.getHighestPriority().ordinal()) myDate.setHighestPriority(duty.getPriority());
                break;
            }
        }
        List<MyDate> listToSubmit = new ArrayList<>(dateList);
        dates.setValue(listToSubmit);
    }

    public void removeObligation(Duty duty){
        List<Duty> dd;
        for(MyDate d:dateList){
            if(d.getDate().equals(duty.getDate())){
                dd = new ArrayList<>(d.getDutyList());
                dd.remove(duty);
                d.setDutyList(dd);
                d.updateHighestPriority();
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
