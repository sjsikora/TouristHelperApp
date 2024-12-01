package com.example.touristhelperapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class BaseActivity extends AppCompatActivity  {

    /*
        Here is a way to share functions between activities.
        Any activity should extend this class.
     */

    /**
     * This function will create a Trip Fragment.
     *
     * @param id The ID of the fragment container R.id.<name>
     * @param tripName The name of the trip
     * @param startDate The start date of the trip
     * @param endDate The end date of the trip
     */
    protected void createTripFragment(int id, String tripName, String startDate, String endDate) {
        // This is a bundle, it works just like how intent does, but for
        // fragments. We put in key value pairs that will be read by our fragment.
        Bundle tripBundle = new Bundle();
        tripBundle.putString("tripName", tripName);
        tripBundle.putString("startDate", startDate);
        tripBundle.putString("endDate", endDate);

        // The FragmentManager is responsible for managing fragments.
        // This is how we create our fragment with our bundle.
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(id, TripFragment.class, tripBundle, "tag")
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    /**
     * This function will create an Event Icon Fragment for a given id.
     *
     * @param id The ID of the fragment container R.id.<name>
     * @param photoLink The link to the icon of the event
     * @param eventName The name of the event
     */
    protected void createEventIconFragment(int id, String photoLink, String eventName) {
        Bundle eventBundle = new Bundle();
        eventBundle.putString("photoLink", photoLink);
        eventBundle.putString("eventName", eventName);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(id, EventIcon.class, eventBundle, "tag")
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }


}
