package com.example.touristhelperapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddOwnerEvent extends BaseActivity {

    private EditText titleField, descriptionField, dateField, startTimeField, endTimeField, locationField, imageUrlField;
    private CheckBox notabilityCheckbox, uniquenessCheckbox, priceCheckbox, accessibilityCheckbox, aweCheckbox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_event_add); // Ensure your XML file is named correctly

        // Initialize fields
        titleField = findViewById(R.id.eventTitle);
        descriptionField = findViewById(R.id.eventDescription);
        dateField = findViewById(R.id.eventDate);
        startTimeField = findViewById(R.id.eventStartTime);
        endTimeField = findViewById(R.id.eventEndTime);
        locationField = findViewById(R.id.eventLocation);
        imageUrlField = findViewById(R.id.eventImageUrl);

        // Initialize checkboxes
        notabilityCheckbox = findViewById(R.id.factorNotability);
        uniquenessCheckbox = findViewById(R.id.factorUniqueness);
        priceCheckbox = findViewById(R.id.factorPrice);
        accessibilityCheckbox = findViewById(R.id.factorAccessibility);
        aweCheckbox = findViewById(R.id.factorAwe);

        // Submit button
        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v -> handleSubmit());
    }

    private void handleSubmit() {
        // Validate inputs
        String title = titleField.getText().toString().trim();
        String description = descriptionField.getText().toString().trim();
        String dateStr = dateField.getText().toString().trim();
        String startTimeStr = startTimeField.getText().toString().trim();
        String endTimeStr = endTimeField.getText().toString().trim();
        String location = locationField.getText().toString().trim();
        String imageUrl = imageUrlField.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description) || TextUtils.isEmpty(dateStr)
                || TextUtils.isEmpty(startTimeStr) || TextUtils.isEmpty(endTimeStr)
                || TextUtils.isEmpty(location) || TextUtils.isEmpty(imageUrl)) {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse and validate date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.ENGLISH);

        Date date;
        Date startTime;
        Date endTime;

        try {
            date = dateFormat.parse(dateStr);
            startTime = timeFormat.parse(startTimeStr);
            endTime = timeFormat.parse(endTimeStr);

            if (startTime.after(endTime)) {
                Toast.makeText(this, "Start time must be before end time!", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (ParseException e) {
            Toast.makeText(this, "Invalid date or time format!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get factors from checkboxes
        ArrayList<String> factors = new ArrayList<>();
        if (notabilityCheckbox.isChecked()) factors.add("Notability");
        if (uniquenessCheckbox.isChecked()) factors.add("Uniqueness");
        if (priceCheckbox.isChecked()) factors.add("Price");
        if (accessibilityCheckbox.isChecked()) factors.add("Accessibility");
        if (aweCheckbox.isChecked()) factors.add("Awe Factor");

        // Create new event
        Event event = new Event(title, factors, startTime, endTime, description, location, imageUrl);

        // Push to Firebase
        createEventBusinessOwner(event);

        // Show success message and navigate back
        Toast.makeText(this, "Event successfully created!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, OwnerEvent.class));
        finish();
    }
}
