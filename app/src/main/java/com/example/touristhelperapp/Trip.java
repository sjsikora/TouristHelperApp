package com.example.touristhelperapp;

import java.util.ArrayList;
import java.util.Date;

public class Trip implements Comparable<Trip> {

    private String name;
    private Date startDate;
    private Date endDate;
    private ArrayList<Event> events;

    Trip() {}

    public Trip(String name) {
        this.name = name;
        this.events = new ArrayList<>();
    }

    public Trip(String tripName, String startDate, String endDate) {
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ArrayList<Event> getEvents() { return events; }
    public void addEvent(Event event) { this.events.add(event); }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    @Override
    public int compareTo(Trip other) {
        if(this.startDate.after(other.startDate)) {
            return -1;
        } else if(this.startDate.before(other.startDate)) {
            return 1;
        } else {
            return 0;
        }
    }
}