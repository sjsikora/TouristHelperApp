package com.example.touristhelperapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SearchEventResults extends BaseActivity {

    Trip trip;
    Date queryStartDate;
    Date queryEndDate;
    ArrayList<String> factors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_event_results);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();

        trip = intent.getParcelableExtra("trip", Trip.class);

        // If trip is null....
        if(trip == null) {
            factors = intent.getStringArrayListExtra("factorArrayList");
            queryStartDate = intent.getSerializableExtra("date", Date.class);
            queryEndDate = queryStartDate;
        } else {
            factors = trip.getFactors();
            if(factors == null) factors = new ArrayList<>();
            queryStartDate = trip.getStartTime();
            queryEndDate = trip.getEndTime();
        }

        if(trip == null) {
            Toast.makeText(this, "No trip selected", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        getEvents((allEvents) -> {

            Iterator<Event> iterator = allEvents.iterator();
            while (iterator.hasNext()) {
                Event event = iterator.next();

                if (!DateHelper.isDateWithinRange(event.getStartTime(), queryStartDate, queryEndDate)) {
                    iterator.remove();
                    continue;
                }

                boolean vaild = false;

                for (String factor : factors) {
                    if (event.getFactors().contains(factor.toLowerCase())) {
                        vaild = true;
                        break;
                    }
                }

                if (!vaild) {
                    iterator.remove();
                }

            }

            populateSearch(allEvents);
        });
    }


    private void populateSearch(List<Event> matchingEvents) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        for(Event event : matchingEvents) {
            Bundle bun = new Bundle();
            bun.putParcelable("event", event);
            if(trip != null) bun.putString("tripName", trip.getName());

            if(fragmentManager.isStateSaved()) return;

            fragmentManager.beginTransaction()
                    .add(R.id.eventEntry, EventIcon.class, bun, "tag")
                    .commit();
        }
    }



}


