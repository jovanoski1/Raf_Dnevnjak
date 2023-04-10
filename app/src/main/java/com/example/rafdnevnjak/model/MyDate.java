package com.example.rafdnevnjak.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MyDate {

    private LocalDate date;
    private List<Duty> dutyList = new ArrayList<>();
    private DutyPriority highestPriority;



    public MyDate(LocalDate date) {
        this.date = date;
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
