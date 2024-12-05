package com.example.touristhelperapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_event);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        event = intent.getParcelableExtra("event", Event.class);

        eventTitleTextView = findViewById(R.id.eventTitle);
        factorsTextView = findViewById(R.id.Factors);
        startTimeTextView = findViewById(R.id.startTime);
        endTimeTextView = findViewById(R.id.endTime);
        eventDescriptionTextView = findViewById(R.id.eventDescription);
        eventLocationTextView = findViewById(R.id.eventLocation);
        tripDropdown = findViewById(R.id.tripDropdown);
        Button addToTripButton = findViewById(R.id.addToTripButton);

        displayEventInformation();

        runOnUiThread(() -> {
            getTrips(this::populateTripDropdown);
        });

        addToTripButton.setOnClickListener(view -> {
            Trip selectedTrip = (Trip) tripDropdown.getSelectedItem();
            addToTrip(selectedTrip);
        });
    }

    @SuppressLint("SetTextI18n")
    private void displayEventInformation() {
        eventTitleTextView.setText(event.getTitle());
        factorsTextView.setText("Factors: " + String.join(", ", event.getFactors()));
        eventDescriptionTextView.setText(event.getDescription());
        startTimeTextView.setText("Start Time: " + formatDate(event.getStartTime()));
        endTimeTextView.setText("End Time: " + formatDate(event.getEndTime()));
        eventLocationTextView.setText("Location: " + event.getLocation());

    }

    private void populateTripDropdown(ArrayList<Trip> trips) {
        ArrayAdapter<Trip> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                trips
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
