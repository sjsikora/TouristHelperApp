package com.example.touristhelperapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Home extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        retrieveData();
    }

    /**
     * This function will retrieve our data needed for the home page.
     */
    public void retrieveData() {

        int[] ids = {
                R.id.eventFrag1,
                R.id.eventFrag2,
                R.id.eventFrag3,
                R.id.eventFrag4
        };


        new Thread(new Runnable() {
            @Override
            public void run() {
                // Get trips from the DB
                getTrips(trips -> {

                    Calendar c = Calendar.getInstance();
                    Date today = c.getTime();

                    if(trips == null || trips.isEmpty()) return;

                    // Only show trips after today
                    trips.removeIf(trip -> trip.getEndTime().before(today));

                    // Sort the trips so the next trip is the first one
                    Collections.sort(trips);

                    // Get that trip and display
                    Trip nextTrip = trips.get(0);
                    runOnUiThread(() -> {
                        createTripFragment(R.id.tripFragment, nextTrip);
                    });


                    getEvents(events -> {

                        events.removeIf(event -> !DateHelper.isDateWithinRange(event.getStartTime(),
                                nextTrip.getStartTime(),
                                nextTrip.getEndTime()));


                        runOnUiThread( () -> {

                            int limit = ids.length;

                            if(events.size() < ids.length) limit = events.size();

                            TextView noEvents = findViewById(R.id.noEventsFound);
                            TextView recommendedEvents = findViewById(R.id.recommendedEvents);

                            recommendedEvents.setText("Recommended Events for your " + nextTrip.getName() + " trip:");

                            if (limit == 0) {
                                TextView tnnoEvents = findViewById(R.id.noEventsFound);
                                noEvents.setText("No events were found for " + nextTrip.getName() + "'s date range");
                                return;
                            } else {


                                noEvents.setText("");
                            }

                            for (int i = 0; i < limit; i++) {
                                Event event = events.get(i);
                                createEventIconFragment(ids[i], event, nextTrip.getName());
                            }
                        });
                    });
                });
            }
        }).start();


    }

    /**
     * This is called when user clicks on "See all my Trips"
     *
     * @param view The view that was clicked
     *
     */
     public void seeAllMyTrips(View view) {
         Intent intent = new Intent(this, ViewTrips.class);
         startActivity(intent);
     }

    /**
     * Function called when user clicks on wrench on home screen
     * It will take them to the Admin panel where we can switch to
     * business view and setup FB
     */
    public void showAdminPanel(View view) {
        Intent intent = new Intent(this, AdminPanel.class);
        startActivity(intent);
    }

    public void searchForEvents(View view){
        Intent intent = new Intent(this, SearchForEvents.class);
        startActivity(intent);

    }

    public void addHalloween(View view) {

    }

}