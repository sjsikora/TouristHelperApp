package com.example.touristhelperapp;

import android.os.Bundle;
import android.view.View;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        createTripFragment(R.id.tripFragment,
                "Christmas With Grandma",
                "December 23rd",
                "30th, 2024"
        );

        createEventIconFragment(R.id.eventFragment,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/9/97/The_Earth_seen_from_Apollo_17.jpg/580px-The_Earth_seen_from_Apollo_17.jpg",
                "Earth"
        );

        createEventIconFragment(R.id.eventFragment2,
                "https://www.kelowna.ca/sites/files/1/uploads/banners/inside/2023_city_view_from_knox_mountain.jpg",
                "Kelowna"
        );

        createEventIconFragment(R.id.eventFragment3,
                "https://www.kelowna.ca/sites/files/1/uploads/banners/inside/2023_city_view_from_knox_mountain.jpg",
                "Kelowna"
        );

        createEventIconFragment(R.id.eventFragment4,
                "https://www.kelowna.ca/sites/files/1/uploads/banners/inside/2023_city_view_from_knox_mountain.jpg",
                "Kelowna"
        );
    }

    /**
     * This function will create a Trip Fragment.
     *
     * @param id The ID of the fragment container R.id.<name>
     * @param tripName The name of the trip
     * @param startDate The start date of the trip
     * @param endDate The end date of the trip
     */
    public void createTripFragment(int id, String tripName, String startDate, String endDate) {
        // This is a bundle, it works just like how intent does, but for
        // fragments. We put in key value pairs that will be read by our fragment.
        Bundle tripBundle = new Bundle();
        tripBundle.putString("tripName", tripName);
        tripBundle.putString("startDate", startDate);
        tripBundle.putString("endDate", endDate);

        // The FragmentManager is responsible for managing fragments.
        // This is how we create our fragment with our bundle.
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(id, TripFragment.class, tripBundle, "tag")
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    /**
     * This function will create an Event Icon Fragment for a given id.
     *
     * @param id The ID of the fragment container R.id.<name>
     * @param photoLink The link to the icon of the event
     * @param eventName The name of the event
     */
    public void createEventIconFragment(int id, String photoLink, String eventName) {
        Bundle eventBundle = new Bundle();
        eventBundle.putString("photoLink", photoLink);
        eventBundle.putString("eventName", eventName);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(id, EventIcon.class, eventBundle, "tag")
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    /**
     * This is called when user clicks on "See all my Trips"
     *
     * @param View The view that was clicked
     *
     */
     public void seeAllMyTrips(View view) {
         // TODO implement screen change
     }

}