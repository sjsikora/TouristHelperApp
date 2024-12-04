package com.example.touristhelperapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TripFragment extends Fragment {

    private static final String ARG_TRIP = "trip";

    private Trip trip;

    TextView tripNameTextView;
    TextView calenderNameTextView;

    @Override
    // After a fragment is created, run this function.
    // Put any logic for the fragment in here. If you put it anywhere else it won't work.
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        tripNameTextView = view.findViewById(R.id.tripName);
        calenderNameTextView = view.findViewById(R.id.dateName);

        if(trip == null) return;

        tripNameTextView.setText(trip.getName());
        calenderNameTextView.setText(String.format("%s - %s", trip.getStartDate(), trip.getEndDate()));

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            trip = getArguments().getParcelable(ARG_TRIP, Trip.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trip, container, false);
    }

    public void onNameClick(View view) {

        Bundle bun = new Bundle();
        bun.putParcelable(ARG_TRIP, trip);

        Intent intent = new Intent(getActivity(), Itinerary.class);
        startActivity(intent);

    }
}