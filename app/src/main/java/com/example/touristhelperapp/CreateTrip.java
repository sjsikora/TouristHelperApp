package com.example.touristhelperapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CreateTrip extends BaseActivity {

    EditText tripName;
    EditText startDateEditText;
    EditText endDateEditText;
    CheckBox notability, uniqueness, price, accessibility, aweFactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_trip);

        Intent intent = getIntent();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fragmentManagerCreator(R.id.homeButton, HomeButtonFragment.class, null);
    }

    public void onClickGoHome(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void onSubmitCreateNewTrip(View view) {
        boolean validDate = false;
        ArrayList<Event> events = new ArrayList<>();
        ArrayList<String> factors = new ArrayList<>();

        tripName = findViewById(R.id.tripNameEditText); // User's input for what they want to name their trip
        String newTripName = tripName.getText().toString();

        startDateEditText = findViewById(R.id.editTextDate); // User's input for trip's start date
        String tripStartDate = startDateEditText.getText().toString();

        endDateEditText = findViewById(R.id.editTextDate2); // User's input for trip's start date
        String tripEndDate = endDateEditText.getText().toString();

        if(!tripStartDate.isEmpty() && !tripEndDate.isEmpty()){ // check that user has entered a start and end date
            validDate = true;
        }

        notability = findViewById(R.id.checkbox_notability);
        uniqueness = findViewById(R.id.checkbox_uniqueness);
        price = findViewById(R.id.checkbox_price);
        accessibility = findViewById(R.id.checkbox_accessibility);
        aweFactor = findViewById(R.id.checkbox_awe_factor);

        checkFactor(factors, notability);
        checkFactor(factors, uniqueness);
        checkFactor(factors, price);
        checkFactor(factors, accessibility);
        checkFactor(factors, aweFactor);

        if(validDate){
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            dateFormat.setLenient(false); // Ensures strict parsing of date

            try {
                Date startDate = dateFormat.parse(tripStartDate);
                Date endDate = dateFormat.parse(tripEndDate);

                Trip newTrip = new Trip(newTripName, startDate, endDate, events, factors);
                createTrip(newTrip);
            } catch (Exception e) {
                Toast.makeText(this, "Invalid date format. Please use MM/DD/YYYY", Toast.LENGTH_SHORT).show();
            }
            Toast toast = Toast.makeText(this, "New trip created.", Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(this, ViewTrips.class);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(this, "Invalid Date. Try Again.", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void checkFactor(ArrayList<String> factors, CheckBox c) {
        if(c.isChecked()) factors.add(c.getText().toString().toLowerCase());
    }
}


