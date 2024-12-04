package com.example.touristhelperapp;

import java.util.ArrayList;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class Trip implements Parcelable {

    private String name;
    private Date startDate;
    private Date endDate;
    private ArrayList<Event> events;
    private ArrayList<String> factors;

    public Trip() {}

    public Trip(String name, Date startDate, Date endDate, ArrayList<Event> events, ArrayList<String> factors) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.events = events;
        this.factors = factors;
    }

    // PARCELABLE METHODS

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeSerializable(startDate);
        out.writeSerializable(endDate);
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
        startDate = (Date) in.readSerializable();
        endDate = (Date) in.readSerializable();
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

    public ArrayList<String> getFactors() {
        return factors;
    }

    public void setFactors(ArrayList<String> factors) {
        this.factors = factors;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", events=" + events +
                ", factors=" + factors +
                '}';
    }

}