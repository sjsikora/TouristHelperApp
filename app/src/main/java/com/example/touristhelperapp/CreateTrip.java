package com.example.touristhelperapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateTrip extends BaseActivity {

    EditText tripName;
    EditText startDate;
    EditText endDate;
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
        tripName = findViewById(R.id.tripNameEditText); // User's input for what they want to name their trip
        String newTripName = tripName.getText().toString();

        startDate = findViewById(R.id.editTextDate); // User's input for trip's start date
        String[] splitStartDate = startDate.getText().toString().split("/");
        String startMonth = splitStartDate[0];
        String startDay = splitStartDate[1];
        String startYear = splitStartDate[2];

        endDate = findViewById(R.id.editTextDate2); // User's input for trip's start date
        String[] splitEndDate = endDate.getText().toString().split("/");
        String endMonth = splitEndDate[0];
        String endDay = splitEndDate[1];
        String endYear = splitEndDate[2];

        notability = findViewById(R.id.checkbox_notability);
        boolean notabilityChecked = notability.isChecked();

        uniqueness = findViewById(R.id.checkbox_uniqueness);
        boolean uniquenessChecked = uniqueness.isChecked();

        price = findViewById(R.id.checkbox_price);
        boolean priceChecked = price.isChecked();

        accessibility = findViewById(R.id.checkbox_accessibility);
        boolean accessibilityChecked = accessibility.isChecked();

        aweFactor = findViewById(R.id.checkbox_awe_factor);
        boolean aweFactorChecked = aweFactor.isChecked();








    }
    public void onClickGoHome(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }









}


