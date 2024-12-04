package com.example.touristhelperapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Date;

public class ItineraryDay extends Fragment {

    static final String ARG_PARAM1 = "listOfEvents";
    private ArrayList<Event> eventsList;

    int uniqueContainerId;

    public ItineraryDay() {}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(eventsList == null) {
            Event e = new Event("Christmas With Grandma", null, new Date(), new Date(),
                    "Grandma's birthday", "location", "imageURL");

            for(int i = 0; i < 2; i++) {
                Bundle bun = new Bundle();
                bun.putParcelable("event", e);

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(uniqueContainerId, ItineraryEvent.class, bun, "tag" + i)
                        .commit();

            }

            return;

        }

        /*
        // For every event in this data make a new Itinerary Event
        for (Event event : eventsList) {

            Bundle bun = new Bundle();
            bun.putParcelable("event", event);

            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(uniqueContainerId, ItineraryEvent.class, bun, "tag")
                    .commit();
        }

         */
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventsList = getArguments().getParcelableArrayList(ARG_PARAM1, Event.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_itinerary_day, container, false);

        // Generate a unique ID
        uniqueContainerId = View.generateViewId();

        // Find the LinearLayout and set the unique ID
        LinearLayout eventContainer = view.findViewById(R.id.eventContainer);
        eventContainer.setId(uniqueContainerId);

        return view;
    }
}