package com.example.touristhelperapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchForEvents extends BaseActivity {
    Spinner tripSpinner;
    EditText tripName;
    EditText date;
    EditText startTime;
    EditText endTime;
    CheckBox notability, uniqueness, price, accessibility, aweFactor;

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
    public void onClickSubmit(View view){
        ArrayList<String> factors = new ArrayList<>(); // list of factors selected by user
        Bundle bundle = new Bundle();


        date = findViewById(R.id.searchDate);
        String searchDate = date.getText().toString(); // get the date entered by user

        startTime = findViewById(R.id.startTimeEditText);
        String searchStartTime = startTime.getText().toString(); // get the start time entered by user

        endTime = findViewById(R.id.endTimeEditText);
        String searchEndTime = endTime.getText().toString(); // get the end time entered by user


        if(!searchDate.isEmpty() && !searchStartTime.isEmpty() && !searchEndTime.isEmpty()){
            try {
                Date dateObj = new SimpleDateFormat("MM/dd/yyyy").parse(searchDate);
                bundle.putSerializable("date", dateObj);

                Date startTimeObj = new SimpleDateFormat("HH:mm").parse(searchStartTime);
                bundle.putSerializable("startTime", startTimeObj);

                Date endTimeObj = new SimpleDateFormat("HH:mm").parse(searchEndTime);
                bundle.putSerializable("endTime", endTimeObj);
            } catch (Exception e) {
                Toast toast = Toast.makeText(this, "Invalid Date. Try Again.", Toast.LENGTH_SHORT);
                toast.show();
            }

            notability = findViewById(R.id.checkbox_notability);
            uniqueness = findViewById(R.id.checkbox_uniqueness);
            price = findViewById(R.id.checkbox_price);
            accessibility = findViewById(R.id.checkbox_accessibility);
            aweFactor = findViewById(R.id.checkbox_awe_factor);

            factors.add(notability.getText().toString());
            factors.add(uniqueness.getText().toString());
            factors.add(price.getText().toString());
            factors.add(accessibility.getText().toString());
            factors.add(aweFactor.getText().toString());
        } else {
            Toast toast = Toast.makeText(this, "Invalid Date. Try Again.", Toast.LENGTH_SHORT);
            toast.show();
        }

        Intent intent = new Intent(this, SearchEventResults.class);
        bundle.putString("tripName", tripSpinner.getSelectedItem().toString());
        bundle.putStringArrayList("factorArrayList", factors);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}