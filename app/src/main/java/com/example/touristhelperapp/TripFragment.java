package com.example.touristhelperapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TripFragment extends Fragment {

    private static final String ARG_TRIPNAME = "tripName";
    private static final String ARG_STARTDATE = "startDate";
    private static final String ARG_ENDDATE = "endDate";

    private String tripName;
    private String startDate;
    private String endDate;

    TextView tripNameTextView;
    TextView calenderNameTextView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        tripNameTextView = view.findViewById(R.id.tripName);
        calenderNameTextView = view.findViewById(R.id.dateName);

        tripNameTextView.setText(tripName);
        calenderNameTextView.setText(String.format("%s - %s", startDate, endDate));

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tripName = getArguments().getString(ARG_TRIPNAME);
            startDate = getArguments().getString(ARG_STARTDATE);
            endDate = getArguments().getString(ARG_ENDDATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trip, container, false);
    }
}