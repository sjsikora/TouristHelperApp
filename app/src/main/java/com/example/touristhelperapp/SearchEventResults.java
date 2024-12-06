package com.example.touristhelperapp;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchEventResults extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_event_results);

        Intent intent = getIntent();
        String tripName = intent.getStringExtra("tripName");
        ArrayList<String> factors = intent.getStringArrayListExtra("factorArrayList");
        Date date = (Date) intent.getSerializableExtra("date");
        Date startTime = (Date) intent.getSerializableExtra("startTime");
        Date endTime = (Date) intent.getSerializableExtra("endTime");
        List<Event> matchingEvents = new ArrayList<>();

        /*
         * TODO: Get all events
         *  Put events that fall within date into a new list
         *  Filter further by start and end time
         *  Filter further by factors
         *  Show events and give option to add to trip
         */



        getTrips(allTrips -> {
                for (Trip trip : allTrips) {
                    if(trip.getName().equals(tripName)){
                        // Check if the date of the event (e.g. 12/1/2024) of the search is within the start and end date of the trip
                        if(checkDate(startTime, trip.getStartTime(), trip.getEndTime()) && checkDate(endTime, trip.getStartTime(), trip.getEndTime())){
                            // For all events in the DB,
                            getEvents((allEvents) -> {
                                for(Event event : allEvents) {
                                    if(checkDate(startTime, event.getStartTime(), event.getEndTime()) && (checkDate(endTime, event.getStartTime(), event.getEndTime()))){ //get only events within the search's start and end time
                                        for(String searchFactor : factors){
                                            if(event.getFactors().contains(searchFactor)){
                                                matchingEvents.add(event); //  and also have at least one factor in common with the search's factors
                                                break;
                                            }
                                        }
                                    }
                                }
                                matchingEvents.removeAll(trip.getEvents());
                            });
                        }

                    }

                }


                FragmentManager fragmentManager = getSupportFragmentManager();
                for(Event event : matchingEvents) {

                    Bundle bun = new Bundle();
                    bun.putParcelable("event", event);

                    fragmentManager.beginTransaction()
                            .replace(R.id.searchScrollView, TripFragment.class, bun, "tag")
                            .commit();


                }

            });





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    boolean checkDate(Date searchDate, Date tripStartDate, Date tripEndDate){
        return !(searchDate.after(tripEndDate) || searchDate.before(tripStartDate));
    }
}


