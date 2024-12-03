package com.example.touristhelperapp;
import java.util.Date;


public class Event {
    private String title;
    private String[] factors;
    private Date dateTime;
    private String description;
    private String location;

    public Event() {}

    public Event(String title, String[] factors, String description, Date dateTime, String location) {
        this.title = title;
        this.factors = factors;
        this.description = description;
        this.dateTime = dateTime;
        this.location = location;
    }

    public String[] getFactors() {
        return factors;
    }

    public void setFactors(String[] factors) {
        this.factors = factors;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getDateTime() { return dateTime; }
    public void setDateTime(Date dateTime) { this.dateTime = dateTime; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
