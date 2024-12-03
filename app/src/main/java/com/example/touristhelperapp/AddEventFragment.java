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


    private TextView eventTitle;
    private TextView eventDescription;
    private TextView eventDateTime;
    private TextView eventLocation;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TextView eventTitle = view.findViewById(R.id.eventTitle);
        TextView eventDescription = view.findViewById(R.id.eventDescription);
        TextView eventDateTime = view.findViewById(R.id.eventDateTime);
        TextView eventLocation = view.findViewById(R.id.eventLocation);
        tripDropdown = view.findViewById(R.id.tripDropdown);
        Button addToTripButton = view.findViewById(R.id.addToTripButton);


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            eventTitle.setText(args.getString("title", "Unknown Title"));
            eventDescription.setText(args.getString("description", "No Description"));
            eventDateTime.setText(args.getString("dateTime", "Unknown Date"));
            eventLocation.setText(args.getString("location", "Unknown Location"));
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trip, container, false);
    }


}

