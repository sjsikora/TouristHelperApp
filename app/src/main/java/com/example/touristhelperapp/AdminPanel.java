package com.example.touristhelperapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

public class AdminPanel extends BaseActivity {

    TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_panel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        errorMessage = findViewById(R.id.errorMessage);

    }

    /**
     * OnClick event that will send users to the business side of the App
     */
    public void goToBusiness(View view) {
        // TODO: ComputerWhiz24 Please fill in
    }


    /**
     * Load standard data into the DB.
     */
    public void loadStandardData(View view) {
        initializeFB();

        // Delete all data
        root.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    // If all data, was deleted go ahead and upload standard data
                    uploadMyStandardData();
                } else {
                    throw new RuntimeException("Failed to delete all data");
                }
            }
        });

    }

    public void uploadMyStandardData() {
        ArrayList<Trip> trips = getMyStandardTrips();
        ArrayList<Event> events = getMyStandardEvents();

        try {
            for(Event event : events) createEvent(event);
            for (Trip trip : trips) createTrip(trip);

        } catch (Exception | Error e ) {
            errorMessage.setText(e.getMessage());
        }

    }

    static public ArrayList<Event> getMyStandardEvents() {

        ArrayList<Event> events = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        cal.set(2025, Calendar.JANUARY, 3, 8, 0);
        Date time1 = cal.getTime();
        cal.set(2025, Calendar.JANUARY, 3, 11, 0);
        Date time2 = cal.getTime();

        events.add(new Event(
                "Starry Night Stargazing",
                new ArrayList<String>(List.of("price", "accessibility")),
                time1,
                time2,
                "Join us for an unforgettable evening under the stars! Starry Nights is a family-friendly event where you can explore the wonders of the night sky. Led by experienced astronomers, we'll guide you through constellations, planets, and other celestial objects visible that night.",
                "Okanagan Observatory, Beaverdell, BC V0H 1A0",
                "https://science.nasa.gov/wp-content/uploads/2024/02/hubble-ngc2298-acs-wfc3-v3-5fcont-final.jpg?w=1891"
        ));

        cal.set(2025, Calendar.JANUARY, 3, 15, 0);
        Date time3 = cal.getTime();
        cal.set(2025, Calendar.JANUARY, 3, 20, 30);
        Date time4 = cal.getTime();

        events.add(new Event(
                "Pasta Making Class",
                new ArrayList<String>(List.of("accessibility")),
                time3,
                time4,
                "Learn how to make Pasta from local Kelowna chefs! ",
                "510 Bernard Ave #100, Kelowna, BC V1Y 6P1",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQrtN4zmU3zFWphWnmErJvFd5Sv0eF2s-e-GA&s"
        ));

        cal.set(2025, Calendar.JANUARY, 6, 9, 30);
        Date time5 = cal.getTime();
        cal.set(2025, Calendar.JANUARY, 6, 12, 0);
        Date time6 = cal.getTime();

        events.add(new Event(
                "Stuart Park Ice Skating",
                new ArrayList<String>(List.of("price", "notability")),
                time5,
                time6,
                "Come ice skating in Kelowna's public park rink! Skate rentals are also available.",
                "1430 Water St, Kelowna, BC V1Y 1J1",
                "https://www.kelowna.ca/sites/files/1/styles/inside_banner/public/uploads/banners/inside/parks/stuart_park_rink.jpg?itok=DfylgVyX"
        ));

        cal.set(2025, Calendar.JANUARY, 4, 14, 30);
        Date time7 = cal.getTime();
        cal.set(2025, Calendar.JANUARY, 4, 15, 0);
        Date time8 = cal.getTime();

        events.add(new Event(
                "Wine Tasting",
                new ArrayList<String>(List.of("notability", "uniqueness", "accessibility")),
                time7,
                time8,
                "Come to Mission Hill for the 3rd annual Winter wine tasting. There will be performances from local bands as well as a Christmas Play.",
                "Mission Hill Family Estate Winery, 1730 Mission Hill Rd, West Kelowna, BC V4T 2E4",
                "https://www.kelowna.ca/sites/files/1/styles/inside_banner/public/uploads/banners/inside/parks/stuart_park_rink.jpg?itok=DfylgVyX"
        ));


        return events;
    }

    public ArrayList<Trip> getMyStandardTrips() {
        ArrayList<Trip> trips = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.JANUARY, 2, 0, 0);
        Date time1 = cal.getTime();

        cal.set(2025, Calendar.JANUARY, 4, 11, 0);
        Date time2 = cal.getTime();


        trips.add(new Trip(
                "Christmas with Grandma",
                time1,
                time2,
                null,
                new ArrayList<String>(List.of("notability", "uniqueness", "accessibility"))
        ));

        return trips;
    }

}

