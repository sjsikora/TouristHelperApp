package com.example.touristhelperapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchEventResults extends BaseActivity {
    private TextView tripNameTextView, dateTextView, timeTextView, factorsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_event_results);

        // Initialize UI components
        tripNameTextView = findViewById(R.id.tripNameTextView);
        dateTextView = findViewById(R.id.dateTextView);
        timeTextView = findViewById(R.id.timeTextView);
        factorsTextView = findViewById(R.id.factorsTextView);

        Intent intent = getIntent();

        // Apply edge-to-edge UI settings
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Retrieve data from the intent
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            // Retrieve trip name
            String tripName = bundle.getString("tripName", "N/A");
            tripNameTextView.setText("Trip Name: " + tripName);

            // Retrieve date
            Date date = (Date) bundle.getSerializable("date");
            if (date != null) {
                String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
                dateTextView.setText("Date: " + formattedDate);
            }

            // Retrieve time range
            Date startTime = (Date) bundle.getSerializable("startTime");
            Date endTime = (Date) bundle.getSerializable("endTime");
            if (startTime != null && endTime != null) {
                String formattedStartTime = new SimpleDateFormat("HH:mm").format(startTime);
                String formattedEndTime = new SimpleDateFormat("HH:mm").format(endTime);
                timeTextView.setText("Time: " + formattedStartTime + " - " + formattedEndTime);
            }

            // Retrieve selected factors
            ArrayList<String> factors = bundle.getStringArrayList("factorArrayList");
            if (factors != null && !factors.isEmpty()) {
                factorsTextView.setText("Selected Factors:\n" + String.join(", ", factors));
            } else {
                factorsTextView.setText("No factors selected.");
            }
        } else {
            tripNameTextView.setText("No data available.");
            dateTextView.setText("");
            timeTextView.setText("");
            factorsTextView.setText("");
        }
    }
}
