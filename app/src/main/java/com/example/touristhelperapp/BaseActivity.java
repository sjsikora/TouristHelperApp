package com.example.touristhelperapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
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

        fragmentManagerCreator(id, TripFragment.class, tripBundle);
        
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

        fragmentManagerCreator(id, EventIcon.class, eventBundle);
    }

    /**
     * This function will call the FragmentManager to create a new fragment
     * for specified id and class with associated bundle
     *
     * @param id The ID of the fragment container
     * @param fragmentClass The class that you want to create a fragment out of
     * @param bun The bundle of arugments that will be sent to the fragment.
     */

    protected void fragmentManagerCreator(
            int id,
            @NonNull Class<? extends androidx. fragment. app. Fragment> fragmentClass,
            Bundle bun
    ) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(id, fragmentClass, bun, "tag")
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();

    }


}
