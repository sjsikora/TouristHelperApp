package com.example.touristhelperapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ViewTrips extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_trips);

        Intent intent = getIntent();
        getTrips(allTrips -> {

            FragmentManager fragmentManager = getSupportFragmentManager();
            int counter = 0; // Or use trip.getId() if available


            for (Trip trip : allTrips) {

                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                String startDate = formatter.format(trip.getStartTime());
                String endDate = formatter.format(trip.getEndTime());

                Bundle tripBundle = new Bundle();
                tripBundle.putString("tripName", trip.getName());
                tripBundle.putString("startDate", startDate);
                tripBundle.putString("endDate", endDate);

                fragmentManager.beginTransaction()
                        .add(R.id.viewTrips, TripFragment.class, tripBundle, "tag")
                        .commit();

            }


        }); // Get all trips




        return;

        /*


            String uniqueTag = "tripFragment" + counter++; // Or "tripFragment" + trip.getId()

            fragmentManager.beginTransaction()
                    .add(R.id.viewTrips, TripFragment.class, null, uniqueTag)
                    .commit();
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
*/


    }

    public void onClickGoHome(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    public void onClickCreateTrip(View view) {
        Intent intent = new Intent(this, CreateTrip.class);
        startActivity(intent);
    }


}