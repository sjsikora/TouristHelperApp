package com.example.touristhelperapp;

import android.os.Bundle;

import java.util.Date;
import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItineraryEvent extends Fragment {

    static final String ARG_EVENT = "event";

    private Event event;

    TextView eventTitle;
    TextView eventTime;

    public ItineraryEvent() {}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventTitle = view.findViewById(R.id.eventTitle);
        eventTime = view.findViewById(R.id.eventTime);

        eventTitle.setText(event.getTitle());
        eventTime.setText(DateHelper.startTimeToEndTime(event.getStartTime(), event.getEndTime()));

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            event = getArguments().getParcelable(ARG_EVENT, Event.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_itinerary_event, container, false);
    }


}