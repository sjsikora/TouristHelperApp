package com.example.touristhelperapp;

import java.util.ArrayList;
import java.util.Date;

public class Trip {
    private String name;
    private Date startDate;
    private Date endDate;
    private ArrayList<Event> events;
    private String[] factors;


    public Trip() {}

    public Trip(String name, Date startDate, Date endDate, ArrayList<Event> events, String[] factors) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.events = events;
        this.factors = factors;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
}