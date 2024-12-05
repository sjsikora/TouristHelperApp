package com.example.touristhelperapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Home extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);  // Expand the SearchView by default
        searchView.setQuery("", false);  // Trigger hint display
        searchView.setQueryHint("Event Name");

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

        // Get trips from the DB
        getTrips(trips -> {

            if(trips == null || trips.isEmpty()) return;


            // Sort the trips so the next trip is the first one
            Collections.sort(trips);

            // Get that trip and display
            Trip nextTrip = trips.get(0);
            createTripFragment(R.id.tripFragment, nextTrip);
        });



        int[] ids = {
                R.id.eventFrag1,
                R.id.eventFrag2,
                R.id.eventFrag3,
                R.id.eventFrag4
        };

        getEvents(events -> {

            int limit = ids.length;

            if(events.size() < ids.length) limit = events.size();

            for(int i = 0; i < limit; i++) {
                Event event = events.get(i);
                createEventIconFragment(ids[i], event);
            }

        });
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

}