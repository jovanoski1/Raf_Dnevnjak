package com.example.rafdnevnjak.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MyDate {

    private LocalDate date;
    private List<Duty> dutyList = new ArrayList<>();
    private DutyPriority highestPriority;

    public void updateHighestPriority(){
        DutyPriority dd=DutyPriority.NO;
        for(Duty d:dutyList){
            if(dd==null) dd = d.getPriority();
            else if(d.getPriority().ordinal() > dd.ordinal()) dd = d.getPriority();
        }
        highestPriority = dd;
    }

    public MyDate(LocalDate date) {
        this.date = date;
        highestPriority = DutyPriority.NO;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Duty> getDutyList() {
        return dutyList;
    }

    public void setDutyList(List<Duty> dutyList) {
        this.dutyList = dutyList;
    }

    public DutyPriority getHighestPriority() {
        return highestPriority;
    }

    public void setHighestPriority(DutyPriority highestPriority) {
        this.highestPriority = highestPriority;
    }
}
