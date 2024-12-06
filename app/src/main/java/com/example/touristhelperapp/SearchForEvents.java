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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SearchForEvents extends BaseActivity {
    Spinner tripSpinner;
    EditText tripName;
    EditText date;
    EditText startTime;
    EditText endTime;
    CheckBox notability, uniqueness, price, accessibility, aweFactor;
    boolean doneLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_for_events);
        Intent intent = getIntent();

        // Put trip names into the spinner

        tripSpinner = findViewById(R.id.selectTripSpinner);
        getTrips(allTrips -> { // get the names of all trips

            // Dynamically update the spinner with the trip names
            ArrayAdapter<Trip> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    allTrips // Use the dynamically fetched trip names
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            tripSpinner.setAdapter(adapter); // Set the adapter on the spinner
            doneLoading = true;
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onClickSubmit(View view){

        if(!doneLoading) {
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, SearchEventResults.class);
        ArrayList<String> factors = null;

        date = findViewById(R.id.searchDate);
        String searchDate = date.getText().toString(); // get the date entered by user

        startTime = findViewById(R.id.startTimeEditText);
        String searchStartTime = startTime.getText().toString(); // get the start time entered by user

        endTime = findViewById(R.id.endTimeEditText);
        String searchEndTime = endTime.getText().toString(); // get the end time entered by user


        if(!searchDate.isEmpty() && !searchStartTime.isEmpty() && !searchEndTime.isEmpty()){
            try {
                Date dateObj = new SimpleDateFormat("MM/dd/yyyy").parse(searchDate);
                intent.putExtra("date", dateObj);

                Date startTimeObj = new SimpleDateFormat("HH:mm", Locale.US).parse(searchStartTime);
                Date endTimeObj = new SimpleDateFormat("HH:mm", Locale.US).parse(searchEndTime);

                assert dateObj != null;
                LocalDate datePart = dateObj.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                LocalTime startTimePart = startTimeObj.toInstant()
                                .atZone(ZoneId.systemDefault())
                                        .toLocalTime();

                LocalTime endTimePart = endTimeObj.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalTime();

                LocalDateTime combinedStartDateTime = LocalDateTime.of(datePart, startTimePart);
                LocalDateTime combinedEndDateTime = LocalDateTime.of(datePart,endTimePart);
                startTimeObj = Date.from(combinedStartDateTime.atZone(ZoneId.systemDefault()).toInstant());
                endTimeObj = Date.from(combinedEndDateTime.atZone(ZoneId.systemDefault()).toInstant());

                intent.putExtra("startTime", startTimeObj);
                intent.putExtra("endTime", endTimeObj);

            } catch (Exception e) {
                Toast toast = Toast.makeText(this, "Invalid Date. Try Again.", Toast.LENGTH_SHORT);
                toast.show();
            }

            factors = new ArrayList<>();
            notability = findViewById(R.id.checkbox_notability2);
            uniqueness = findViewById(R.id.checkbox_uniqueness2);
            price = findViewById(R.id.checkbox_price2);
            accessibility = findViewById(R.id.checkbox_accessibility2);
            aweFactor = findViewById(R.id.checkbox_awe_factor2);

            if(notability.isChecked()) { factors.add(notability.getText().toString()); }
            if(uniqueness.isChecked()) { factors.add(uniqueness.getText().toString()); }
            if (price.isChecked()) { factors.add(price.getText().toString()); }
            if (accessibility.isChecked()) { factors.add(accessibility.getText().toString()); }
            if (aweFactor.isChecked()) { factors.add(aweFactor.getText().toString()); }
        } else {
            Toast toast = Toast.makeText(this, "Invalid Date. Try Again.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        Trip selectedTrip = (Trip) tripSpinner.getSelectedItem();

        intent.putExtra("trip", selectedTrip);
        intent.putExtra("factorArrayList", factors);

        startActivity(intent);

    }
}