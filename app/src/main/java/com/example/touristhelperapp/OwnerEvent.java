package com.example.touristhelperapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class OwnerEvent extends BaseActivity {
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_owner_event);
        scrollView = findViewById(R.id.scrollView);
        LinearLayout layout = findViewById(R.id.layout);

        getAllBusinessOwnerEvents(events -> {
            for (Event e : events) {
                // Create a vertical LinearLayout for each event (for title and date)
                LinearLayout eventLayout = new LinearLayout(this);
                eventLayout.setOrientation(LinearLayout.HORIZONTAL);
                eventLayout.setPadding(16, 16, 16, 16);

                // Create an ImageView for the event image
                ImageView imageView = new ImageView(this);
                LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(150, 150);
                imageParams.setMargins(0, 0, 16, 0);
                imageView.setLayoutParams(imageParams);

                // Create a vertical layout for title and date
                LinearLayout textLayout = new LinearLayout(this);
                textLayout.setOrientation(LinearLayout.VERTICAL);

                // Create a TextView for the event title
                TextView titleView = new TextView(this);
                titleView.setText(e.getTitle());
                titleView.setTextSize(18);

                // Create a TextView for the event date
                TextView dateView = new TextView(this);
                dateView.setTextSize(14);
                dateView.setPadding(0, 8, 0, 0);
                dateView.setText(formatEventDate(e.getStartTime(), e.getEndTime()));

                // Add title and date TextViews to the text layout
                textLayout.addView(titleView);
                textLayout.addView(dateView);

                // Add the ImageView and text layout to the event layout
                eventLayout.addView(imageView);
                eventLayout.addView(textLayout);

                // Add the event layout to the main layout
                layout.addView(eventLayout);

                // Load the image asynchronously
                loadImageFromURL(e.getImageURL(), imageView);
            }
        });
    }

    private String formatEventDate(Date startTime, Date endTime) {
        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d 'at' h:mm a", Locale.ENGLISH);
        SimpleDateFormat endTimeFormat = new SimpleDateFormat("h:mm a", Locale.ENGLISH);

        // Format start and end times
        String formattedStartTime = dateFormat.format(startTime);
        String formattedEndTime = endTimeFormat.format(endTime);

        // Combine into a single string
        return formattedStartTime + " - " + formattedEndTime;
    }

    private void loadImageFromURL(String urlString, ImageView imageView) {
        new Thread(() -> {
            try {
                URL url = new URL(urlString);
                Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                // Set the bitmap on the UI thread
                runOnUiThread(() -> imageView.setImageBitmap(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void addBusinessEvent() {
        Intent intent = new Intent(OwnerEvent.this, ViewTrips.class);
        startActivity(intent);
    }

    public void editBusinessEvent() {
        Intent intent = new Intent(OwnerEvent.this, ViewTrips.class);
        startActivity(intent);
    }
}
