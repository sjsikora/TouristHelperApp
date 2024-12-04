package com.example.touristhelperapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        createTripFragment(R.id.tripFragment,
                "Christmas With Grandma",
                "December 23rd",
                "30th, 2024"
        );

        createEventIconFragment(R.id.eventFrag1,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/9/97/The_Earth_seen_from_Apollo_17.jpg/580px-The_Earth_seen_from_Apollo_17.jpg",
                "Earth"
        );

        createEventIconFragment(R.id.eventFrag2,
                "https://www.kelowna.ca/sites/files/1/uploads/banners/inside/2023_city_view_from_knox_mountain.jpg",
                "Kelowna"
        );

        createEventIconFragment(R.id.eventFrag3,
                "https://www.kelowna.ca/sites/files/1/uploads/banners/inside/2023_city_view_from_knox_mountain.jpg",
                "Kelowna"
        );

        createEventIconFragment(R.id.eventFrag4,
                "https://www.kelowna.ca/sites/files/1/uploads/banners/inside/2023_city_view_from_knox_mountain.jpg",
                "Kelowna"
        );
    }

    /**
     * This is called when user clicks on "See all my Trips"
     *
     * @param view The view that was clicked
     *
     */
     public void seeAllMyTrips(View view) {
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

}