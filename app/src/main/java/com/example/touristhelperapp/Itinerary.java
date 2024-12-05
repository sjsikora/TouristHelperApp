package com.example.touristhelperapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Itinerary extends AppCompatActivity {

    TextView tripName;
    TextView tripDate;
    TextView nothingToShowMessage;

    Trip trip = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_itinerary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tripName = findViewById(R.id.tripName);
        tripDate = findViewById(R.id.tripDate);
        nothingToShowMessage = findViewById(R.id.nothingToShowMessage);


        Intent intent = getIntent();
        trip = intent.getParcelableExtra("trip", Trip.class);

        /*
        // Function used for testing

        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.JANUARY, 2, 0, 0);
        Date time1 = cal.getTime();

        cal.set(2025, Calendar.JANUARY, 4, 11, 0);
        Date time2 = cal.getTime();

        Trip trip = new Trip(
                "Christmas with Grandma",
                time1,
                time2,
                AdminPanel.getMyStandardEvents(),
                new ArrayList<>(List.of("notability", "uniqueness", "accessibility"))
        );

        */

        // Case we didnt get anything from intent
        if(trip == null) {
            tripName.setText(R.string.something_went_wrong_no_trip_is_known_to_this_page);
            tripDate.setText("");
            return;
        }

        tripName.setText(trip.getName());
        tripDate.setText(String.format("%s - %s",
                DateHelper.formatDateWithSuffix(trip .getStartTime()),
                DateHelper.formatDateWithSuffix(trip.getEndTime())));

        // Case trip contains no events
        if(trip.getEvents() == null || trip.getEvents().isEmpty()) {
            nothingToShowMessage.setText(R.string.no_events_found);
            return;
        }

        populateItinerary(trip);

    }

    public void populateItinerary(Trip trip) {

        // Create a new HashMap. Want to group events by days
        Map <String, ArrayList<Event>> eventsByDay = new HashMap<>();

        for(Event event : trip.getEvents()) {

            // Get the day of the event formatDateWithSuffix is an easy way to do this
            String eventDay = DateHelper.formatDateWithSuffix(event.getStartTime());

            if(eventsByDay.containsKey(eventDay)) {
                // If the day is already in the hash add it to the arraylist present
                Objects.requireNonNull(eventsByDay.get(eventDay)).add(event);
            } else {
                // If not, place a new arraylist in the hashmap on that day
                eventsByDay.put(eventDay, new ArrayList<Event>(List.of(event)));
            }

        }

        // Get a list of all days
        ArrayList<ArrayList<Event>> listOfEventsByDay = new ArrayList<>(eventsByDay.values());

        // Sort the days so that we have natural ordering
        Collections.sort(listOfEventsByDay, (a, b) -> {
            Event eventA = a.get(0);
            Event eventB = b.get(0);
            return eventA.getStartTime().compareTo(eventB.getStartTime());
        });

        // Put all
        for(int i = 0; i < listOfEventsByDay.size(); i++) {
            Bundle bun = new Bundle();
            bun.putParcelableArrayList("listOfEvents", listOfEventsByDay.get(i));

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.eventDay, ItineraryDay.class, bun, "tag")
                    .commit();

        }
    }

    public void onClickAddMore(View view) {
        Intent intent = new Intent(this, SearchForEvents.class);
        startActivity(intent);
    }
}