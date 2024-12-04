package com.example.touristhelperapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItineraryDay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItineraryDay extends Fragment {

    static final String ARG_PARAM1 = "listOfEvents";
    private ArrayList<Event> eventsList;

    public ItineraryDay() {}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // For every event in this data make a new Itinerary Event
        for (Event event : eventsList) {
            Bundle args = new Bundle();
            args.putParcelable(ItineraryEvent.ARG_EVENT, event);
        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventsList = getArguments().getParcelableArrayList(ARG_PARAM1, Event.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_itinerary_day, container, false);
    }
}