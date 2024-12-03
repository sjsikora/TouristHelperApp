package com.example.touristhelperapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity  {

    DatabaseReference root;

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

    /**
     * This function will initialize a reference to the Firebase object.
     * Run this function before any FB operations.
     */
    protected void initializeFB() {
        root = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Push data into the Firebase DB.
     *
     * @param path The name of what 'folder' the data should be placed into
     * @param object The object to be placed into the DB. NOTE only Trip and Event have been vetted.
     */
    private void pushObject(String path, Object object) {
        if(root == null) initializeFB();

        // Put "cursor" on /<name>
        DatabaseReference tripsRef = root.child(path);

        // Push object into the DB
        tripsRef.push().setValue((Trip)object);
    }

    /**
     * Read objects from a given path and cast them to a class.
     *
     * @param path The path to read from
     * @param classType The class to cast to
     */
    protected <T> ArrayList<T> getObjectsFromPath(String path, Class<T> classType) {
        if(root == null) initializeFB();
        DatabaseReference tripsRef = root.child(path);

        ArrayList<T> list = new ArrayList<>();
        tripsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot tripSnapshot : dataSnapshot.getChildren()) {
                    list.add(tripSnapshot.getValue(classType));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return list;
    }

    /**
     * This function will create a new trip in the DB.
     *
     * @param trip The trip to be added
     */
    protected void createTrip(Trip trip) {
        pushObject("trips", trip);
    }

    /**
     * Return all trips from the DB.
     */
    protected ArrayList<Trip> getTrips() {
        return getObjectsFromPath("trips", Trip.class);
    }

    /**
     * Create a new event in DB.
     *
     */
    protected void createEvent(Event event) {
        pushObject("events", event);
    }

    /**
     * Return all events from the DB.
     *
     */
    protected ArrayList<Event> getEvents() {
        return getObjectsFromPath("events", Event.class);
    }

    /**
     * Create an event as business owner.
     *
     * @param event The event to place in DB
     */
    protected void createEventBusinessOwner(Event event) {
        pushObject("eventsBO", event);
    }

    /**
     * Get all business owner events.
     */
    protected ArrayList<Event> getAllBusinessOwnerEvents() {
        return getObjectsFromPath("eventsBO", Event.class);
    }

}
