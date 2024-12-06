package com.example.touristhelperapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

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
                // Create a horizontal LinearLayout for each event
                LinearLayout eventLayout = new LinearLayout(this);
                eventLayout.setOrientation(LinearLayout.HORIZONTAL);
                eventLayout.setPadding(16, 16, 16, 16);

                // Create an ImageView for the event image
                ImageView imageView = new ImageView(this);
                LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(150, 150);
                imageParams.setMargins(0, 0, 16, 0);
                imageView.setLayoutParams(imageParams);

                // Create a TextView for the event title
                TextView titleView = new TextView(this);
                titleView.setText(e.getTitle());
                titleView.setTextSize(18);

                // Add the ImageView and TextView to the event layout
                eventLayout.addView(imageView);
                eventLayout.addView(titleView);

                // Add the event layout to the main layout
                layout.addView(eventLayout);

                // Load the image asynchronously
                loadImageFromURL(e.getImageURL(), imageView);
            }
        });

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
        Intent intent = new Intent(OwnerEvent.this, OwnerEvent.class);
        startActivity(intent);
    }
    public void editBusinessEvent() {
        Intent intent = new Intent(OwnerEvent.this, OwnerEvent.class);
        startActivity(intent);
    }
}
