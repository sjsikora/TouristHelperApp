package com.example.touristhelperapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AddEventFragment extends TripFragment {

    private Spinner tripDropdown;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);

        TextView eventTitle = view.findViewById(R.id.eventTitle);
        TextView eventDescription = view.findViewById(R.id.eventDescription);
        TextView eventDateTime = view.findViewById(R.id.eventDateTime);
        TextView eventLocation = view.findViewById(R.id.eventLocation);
        tripDropdown = view.findViewById(R.id.tripDropdown);
        Button addToTripButton = view.findViewById(R.id.addToTripButton);

        Bundle args = getArguments();
        if (args != null) {
            eventTitle.setText(args.getString("title", "Unknown Title"));
            eventDescription.setText(args.getString("description", "No Description"));
            eventDateTime.setText(args.getString("dateTime", "Unknown Date"));
            eventLocation.setText(args.getString("location", "Unknown Location"));
        }

        ArrayList<String> tripList = getTripList(); // Implement this to fetch trips
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, tripList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tripDropdown.setAdapter(adapter);

        addToTripButton.setOnClickListener(v -> {
            String selectedTrip = tripDropdown.getSelectedItem().toString();
            addEventToTrip(selectedTrip); // Implement this function to handle the addition
        });

        return view;
    }

    private ArrayList<String> getTripList() {
        //todo - actual logic
        ArrayList<String> trips = new ArrayList<>();
        trips.add("Trip to Paris");
        trips.add("Weekend Getaway");
        trips.add("Family Vacation");
        return trips;
    }

    private void addEventToTrip(String tripName) {
        //todo
    }
}
