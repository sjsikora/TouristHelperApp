package com.example.touristhelperapp;

import java.util.ArrayList;

public class Trip {
    private String name;
    private ArrayList<Event> events;

    public Trip(String name) {
        this.name = name;
        this.events = new ArrayList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ArrayList<Event> getEvents() { return events; }
    public void addEvent(Event event) { this.events.add(event); }
}