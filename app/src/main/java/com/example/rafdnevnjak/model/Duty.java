package com.example.rafdnevnjak.model;

import java.time.LocalTime;

public class Duty {

    private LocalTime startTime;
    private LocalTime endTime;
    private String title;
    private String description;
    private DutyPriority priority;


    public Duty(LocalTime startTime, LocalTime endTime, String title, String description, DutyPriority priority) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DutyPriority getPriority() {
        return priority;
    }

    public void setPriority(DutyPriority priority) {
        this.priority = priority;
    }
}
