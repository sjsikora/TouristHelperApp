package com.example.touristhelperapp;

import java.util.ArrayList;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class Trip implements Parcelable, Comparable<Trip> {

    private String name;
    private Date startTime;
    private Date endTime;
    private ArrayList<Event> events;
    private ArrayList<String> factors;

    public Trip() {}

    public Trip(String name, Date startDate, Date endDate, ArrayList<Event> events, ArrayList<String> factors) {
        this.name = name;
        this.startTime = startDate;
        this.endTime = endDate;
        this.events = events;
        this.factors = factors;
    }

    // PARCELABLE METHODS

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeSerializable(startTime);
        out.writeSerializable(endTime);
        out.writeTypedList(events);
        out.writeStringList(factors);
    }

    public static final Parcelable.Creator<Trip> CREATOR
            = new Parcelable.Creator<Trip>() {
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    private Trip(Parcel in) {
        name = in.readString();
        startTime = (Date) in.readSerializable();
        endTime = (Date) in.readSerializable();
        events = in.createTypedArrayList(Event.CREATOR);
        factors = in.createStringArrayList();
    }

    public int describeContents() {
        return 0;
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

    public ArrayList<String> getFactors() {
        return factors;
    }

    public void setFactors(ArrayList<String> factors) {
        this.factors = factors;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    @Override
    public String toString() {
        return "Trip{" +
                "name='" + name + '\'' +
                ", startDate=" + startTime +
                ", endDate=" + endTime +
                ", events=" + events +
                ", factors=" + factors +
                '}';
    }

    @Override
    public int compareTo(Trip other) {
        if(this.startTime.after(other.startTime)) {
            return 1;
        } else if(this.startTime.before(other.startTime)) {
            return -1;
        } else {
            return 0;
        }
    }
}