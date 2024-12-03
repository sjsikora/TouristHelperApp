package com.example.touristhelperapp;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class Event implements Parcelable {
    private String title;
    private ArrayList<String> factors;
    private Date startTime;
    private Date endTime;
    private String description;
    private String location;
    private String imageURL;

    // Needed for FB
    public Event() {}

    public Event(String title, ArrayList<String> factors, Date startTime, Date endTime,
                 String description, String location, String imageURL) {
        this.title = title;
        this.factors = factors;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.location = location;
        this.imageURL = imageURL;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeStringList(factors);
        out.writeSerializable(startTime);
        out.writeSerializable(endTime);
        out.writeString(description);
        out.writeString(location);
        out.writeString(imageURL);
    }

    public static final Parcelable.Creator<Event> CREATOR
            = new Parcelable.Creator<Event>() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };


    private Event(Parcel in) {
        title = in.readString();
        factors = in.createStringArrayList();
        startTime = (Date) in.readSerializable();
        endTime = (Date) in.readSerializable();
        description = in.readString();
        location = in.readString();
        imageURL = in.readString();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getFactors() {
        return factors;
    }

    public void setFactors(ArrayList<String> factors) {
        this.factors = factors;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title='" + title + '\'' +
                ", factors=" + factors +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
