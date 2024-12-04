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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ItineraryDay extends Fragment {

    static final String ARG_PARAM1 = "listOfEvents";
    private ArrayList<Event> eventsList;

    int uniqueContainerId;

    public ItineraryDay() {}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Calendar cal = Calendar.getInstance();
        cal.setTime(eventsList.get(0).getStartTime());
        String day = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

        TextView tripDayName = view.findViewById(R.id.tripDayName);
        tripDayName.setText(String.format("%s %s", day, month));


        for(Event event : eventsList) {

            Bundle bun = new Bundle();
            bun.putParcelable("event", event);

            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(uniqueContainerId, ItineraryEvent.class, bun, "tag")
                    .commit();
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