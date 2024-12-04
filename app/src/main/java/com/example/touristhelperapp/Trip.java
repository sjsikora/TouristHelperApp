package com.example.touristhelperapp;

import java.util.ArrayList;
import java.util.Date;

public class Trip {
    private String name;
    private Date startTime;
    private Date endTime;
    private ArrayList<Event> events;
    private String[] factors;


    public Trip() {}

    public Trip(String name, Date startDate, Date endDate, ArrayList<Event> events, String[] factors) {
        this.name = name;
        this.startTime = startDate;
        this.endTime = endDate;
        this.events = events;
        this.factors = factors;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startDate) {
        this.startTime = startDate;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endDate) {
        this.endTime = endDate;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public String[] getFactors() {
        return factors;
    }

    public void setFactors(String[] factors) {
        this.factors = factors;
    }

    public void addEvent(Event event) {
        events.add(event);
    }
}