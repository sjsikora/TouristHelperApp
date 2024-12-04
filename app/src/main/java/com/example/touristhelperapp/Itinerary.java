package com.example.touristhelperapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
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

        /*
            Intent intent = getIntent();
            Trip trip = intent.getParcelableExtra("trip", Trip.class);
         */

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
                new ArrayList<String>(List.of("notability", "uniqueness", "accessibility"))
        );

        tripName = findViewById(R.id.tripName);
        tripDate = findViewById(R.id.tripDate);

        tripName.setText(trip.getName());

        Map <String, ArrayList<Event>> eventsByDay = new HashMap<>();

        for(Event event : trip.getEvents()) {
            String eventDay = event.getStartTime().getYear() + "-"
                    + event.getStartTime().getMonth() + "-"
                    + event.getStartTime().getDay();

            if(eventsByDay.containsKey(eventDay)) {
                Objects.requireNonNull(eventsByDay.get(eventDay)).add(event);
            } else {
                eventsByDay.put(eventDay, new ArrayList<Event>(List.of(event)));
            }

        }

        for (Map.Entry<String, ArrayList<Event>> entry : eventsByDay.entrySet()) {
            ArrayList<Event> eventsForDay = entry.getValue();

            Bundle bun = new Bundle();
            bun.putParcelableArrayList("listOfEvents", eventsForDay);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.eventDay, ItineraryDay.class, bun, "tag")
                    .commit();
        }




    }

}