package com.example.touristhelperapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Itinerary extends BaseActivity {

    TextView tripName;
    TextView tripDate;
    TextView nothingToShowMessage;
    ScrollView scrollView;

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
        scrollView = findViewById(R.id.scrollView2);

        putUIinLoadingState();

        Intent intent = getIntent();
        trip = intent.getParcelableExtra("trip", Trip.class);


        // Case we didn't get anything from intent
        if(trip == null) {
            tripName.setText(R.string.something_went_wrong_no_trip_is_known_to_this_page);
            tripDate.setText("");
            return;
        }

        tripName.setText(trip.getName());
        tripDate.setText(String.format("%s - %s",
                DateHelper.formatDateWithSuffix(trip .getStartTime()),
                DateHelper.formatDateWithSuffix(trip.getEndTime())));

        getAllEventsFromTripName(trip.getName(), (events) -> {
            trip.setEvents(events);
            populateItinerary(events);
        });

    }

    public void populateItinerary(ArrayList<Event> events) {
        // Case trip contains no events
        if(events == null || events.isEmpty()) {
            nothingToShowMessage.setText(R.string.no_events_found);
            return;
        }

        // Create a new HashMap. Want to group events by days
        Map <String, ArrayList<Event>> eventsByDay = new HashMap<>();

        for(Event event : events) {

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

        scrollView.setVisibility(View.VISIBLE);
        nothingToShowMessage.setText("");
    }

    public void putUIinLoadingState() {
        scrollView.setVisibility(View.INVISIBLE);
        nothingToShowMessage.setText("Loading data....");
    }

    public void onClickAddMore(View view) {
        Intent intent = new Intent(this, SearchForEvents.class);
        startActivity(intent);
    }
}