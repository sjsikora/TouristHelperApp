package com.example.touristhelperapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class AddEventActivity extends BaseActivity {


    TextView title;
    TextView factors;
    TextView description;
    TextView location;
    TextView textNearCal;
    TextView timeText;

    Spinner tripDropdown;

    ImageView calendarButton;
    ImageView photoView;

    boolean doneLoading = false;

    Bitmap image = null;

    private Event event;

    ArrayList<Trip> trips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_event);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        event = intent.getParcelableExtra("event", Event.class);
        image = intent.getParcelableExtra("image", Bitmap.class);

        title = findViewById(R.id.eventTitle);
        factors = findViewById(R.id.eventFactors);
        description = findViewById(R.id.eventDescription);
        location = findViewById(R.id.eventLocation);
        tripDropdown = findViewById(R.id.tripDropdown);
        calendarButton = findViewById(R.id.calendarButton);
        textNearCal = findViewById(R.id.textNearCalendar);
        photoView = findViewById(R.id.photoView);
        timeText = findViewById(R.id.timeText);

        displayEventInformation();

        loadMainImage();

        getTrips(this::populateTripDropdown);

    }

    @SuppressLint("SetTextI18n")
    private void displayEventInformation() {

        title.setText(event.getTitle());
        factors.setText(String.join(", ", event.getFactors()));
        description.setText(event.getDescription());
        location.setText(event.getLocation());
        textNearCal.setText("Add to trip");

        StringBuilder concat = new StringBuilder();
        Date start = event.getStartTime();
        Date end = event.getEndTime();

        concat.append(DateHelper.formatDateWithSuffix(start));
        concat.append(" ");
        concat.append(DateHelper.startTimeToEndTime(start, end));

        timeText.setText(concat.toString());

    }

    private void populateTripDropdown(ArrayList<Trip> trips) {

        ArrayAdapter<Trip> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new ArrayList<>(trips)
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tripDropdown.setAdapter(adapter);

        tripDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateUIToLoadingState();

                Trip selectedTrip = (Trip) adapterView.getItemAtPosition(i);
                isEventInTrip(selectedTrip.getName(), event.getTitle(), (inTrip) -> {
                    if (inTrip) {
                        updateUIToRemoveFromTrip();
                    } else {
                        updateUIToAddToTrip();
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private String naturalizeFactors(String fact) {

        return fact;
    }

    private void loadMainImage() {
        if(image == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(event.getImageURL());
                        image = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                        // Once we get here, link back into the main thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                photoView.setImageBitmap(image);
                            }
                        });
                    } catch (IOException ignored) {}
                }
            }).start();
        } else {
            photoView.setImageBitmap(image);
        }
    }

    private void updateUIToRemoveFromTrip() {
        Drawable calendarRemove = getResources().getDrawable(R.drawable.calendar_remove);
        calendarButton.setImageDrawable(calendarRemove);
        textNearCal.setText("Remove from trip");
        calendarButton.setEnabled(true);

        calendarButton.setOnClickListener(v -> {

            Trip selectedTrip = (Trip) tripDropdown.getSelectedItem();
            removeFromTrip(selectedTrip.getName(), event, (statusCode) -> {
                switchOnStatus(statusCode, true);
                finish();
            });

        });
    }

    private void updateUIToAddToTrip() {
        Drawable calendarRemove = getResources().getDrawable(R.drawable.calendar_check);
        calendarButton.setImageDrawable(calendarRemove);
        textNearCal.setText("Add to trip");
        calendarButton.setEnabled(true);

        calendarButton.setOnClickListener(v -> {
            Trip selectedTrip = (Trip) tripDropdown.getSelectedItem();
            addEventToTrip(selectedTrip.getName(), event, statusCode -> {
                switchOnStatus(statusCode, false);
                finish();
            });

        });
    }

    private void updateUIToLoadingState() {
        calendarButton.setEnabled(false);
        textNearCal.setText("Add to trip");
    }

    private void switchOnStatus(int code, boolean removing) {

        switch (code) {
            case 409:
                Toast.makeText(this, "This trip does not exists", Toast.LENGTH_SHORT).show();
                break;
            case 410:
                if(removing) {
                    Toast.makeText(this, "This event is not in this trip", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "This event is already in this trip", Toast.LENGTH_SHORT).show();
                }
                break;
            case 200:
                if(removing) {
                    Toast.makeText(this, "Event removed from trip", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Event added to trip", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
