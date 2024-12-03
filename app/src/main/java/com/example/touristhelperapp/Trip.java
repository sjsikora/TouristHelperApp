package com.example.touristhelperapp;

import java.util.ArrayList;
import java.util.Date;

public class Trip {
    private String name;
    private Date startDate;
    private Date endDate;
    private ArrayList<Event> events;

    public Trip() {}

    public Trip(String name) {
        this.name = name;
        this.events = new ArrayList<>();
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

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ArrayList<Event> getEvents() { return events; }
    public void addEvent(Event event) { this.events.add(event); }
}