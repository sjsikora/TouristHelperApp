package com.example.touristhelperapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SearchForEvents extends BaseActivity {

    Spinner tripSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_for_events);

        Intent intent = getIntent();
        ArrayList<String> tripNames = new ArrayList<>();

        tripSpinner = findViewById(R.id.selectTripSpinner);
        getTrips(allTrips -> { // get the names of all trips
            for (Trip trip : allTrips) {
                tripNames.add(trip.getName()); // add each trip name to the list
            }
            // Dynamically update the spinner with the trip names
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    tripNames // Use the dynamically fetched trip names
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            tripSpinner.setAdapter(adapter); // Set the adapter on the spinner

        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}