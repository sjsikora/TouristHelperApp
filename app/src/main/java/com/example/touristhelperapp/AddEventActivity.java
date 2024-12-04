package com.example.touristhelperapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddEventActivity extends BaseActivity {

    private TextView eventTitleTextView;
    private TextView factorsTextView;
    private TextView eventDescriptionTextView;
    private TextView startTimeTextView;
    private TextView endTimeTextView;
    private TextView eventLocationTextView;
    private Spinner tripDropdown;

    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        // Unpack Event object from Intent extras
        Intent intent = getIntent();
        event = (Event) intent.getSerializableExtra("event");

        // Initialize UI elements
        eventTitleTextView = findViewById(R.id.eventTitle);
        factorsTextView = findViewById(R.id.Factors);
        eventDescriptionTextView = findViewById(R.id.eventDescription);
        startTimeTextView = findViewById(R.id.startTime);
        endTimeTextView = findViewById(R.id.endTime);
        eventLocationTextView = findViewById(R.id.eventLocation);
        View eventImageView = findViewById(R.id.eventImage);
        tripDropdown = findViewById(R.id.tripDropdown);
        Button addToTripButton = findViewById(R.id.addToTripButton);

        // Display Event Information
        displayEventInformation();

        // Retrieve Trips and populate the spinner
        runOnUiThread(() -> {
            ArrayList<Trip> trips = getTrips(); // Directly call getTrips()
            populateTripDropdown(trips);
        });

        // Set Button Click Listener
        addToTripButton.setOnClickListener(view -> {
            Trip selectedTrip = (Trip) tripDropdown.getSelectedItem();
            addToTrip(selectedTrip);
        });
    }

    private void displayEventInformation() {
        eventTitleTextView.setText(event.getTitle());
        factorsTextView.setText("Factors: " + String.join(", ", event.getFactors()));
        eventDescriptionTextView.setText(event.getDescription());
        startTimeTextView.setText("Start Time: " + formatDate(event.getStartTime()));
        endTimeTextView.setText("End Time: " + formatDate(event.getEndTime()));
        eventLocationTextView.setText("Location: " + event.getLocation());

        createEventIconFragment(
                R.id.eventImage,
                "https://static.wikia.nocookie.net/jtohs-joke-towers/images/3/33/Fs.de.jpg/revision/latest?cb=20230617010304",
                "change this"
        );
    }

    private void populateTripDropdown(ArrayList<Trip> trips) {
        // Set up the spinner with trips
        ArrayAdapter<Trip> adapter = new ArrayAdapter<>(
                this, // Pass the current context
                android.R.layout.simple_spinner_item,
                trips // Use the passed trips list
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tripDropdown.setAdapter(adapter);
    }

    private void addToTrip(Trip selectedTrip) {
        if (selectedTrip != null) {
            selectedTrip.addEvent(event);
            addEventToTrip(selectedTrip.getName(), event);
            Toast.makeText(this, "Event added to " + selectedTrip.getName(), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Please select a trip", Toast.LENGTH_SHORT).show();
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy h:mm a", Locale.getDefault());
        return sdf.format(date);
    }
}
