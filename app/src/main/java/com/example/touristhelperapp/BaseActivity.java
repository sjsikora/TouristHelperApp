package com.example.touristhelperapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class BaseActivity extends AppCompatActivity  {

    protected DatabaseReference root;

    /*
        Here is a way to share functions between activities.
        Any activity should extend this class.
     */

    /**
     * This function will create a Trip Fragment.
     *
     * @param id The ID of the fragment container R.id.<name>
     * @param trip The trip to be placed in the fragment
     *
     */
    protected void createTripFragment(int id, Trip trip) {
        // This is a bundle, it works just like how intent does, but for
        // fragments. We put in key value pairs that will be read by our fragment.
        Bundle tripBundle = new Bundle();
        tripBundle.putParcelable("trip", trip);

        fragmentManagerCreator(id, TripFragment.class, tripBundle);

    }

    /**
     * This function will create an Event Icon Fragment for a given id.
     *
     * @param id The ID of the fragment container R.id.<name>
     * @param event The event to be placed in the fragment
     */
    protected void createEventIconFragment(int id, Event event) {
        Bundle eventBundle = new Bundle();
        eventBundle.putParcelable("event", event);

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

        if(fragmentManager.isStateSaved()) return;

        fragmentManager.beginTransaction()
                .replace(id, fragmentClass, bun, "tag")
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();

    }

    /**
     * This function will initialize a reference to the Firebase object.
     * Run this function before any FB operations.
     */
    void initializeFB() {
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
        tripsRef.push().setValue(object);
    }

    //like pushObject, but for events. Also serializes it as a String/Object map and stores them with a unique key.
    private void saveEvent(String path, String key, Event event) {

        if (root == null) {
            initializeFB();
        }

        DatabaseReference eventRef = root.child(path);

        if (key == null || key.isEmpty()) {
            //Firebase has a key system we can use to avoid duplicating stuff
            key = eventRef.push().getKey();
        }

        if (key != null) {
            //store as a serialized map instead of just storing the object
            Map<String, Object> eventMap = serializeEvent(event);

            String finalKey = key;
            eventRef.child(key).setValue(eventMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    System.out.println("Event saved at path: " + path + "/" + finalKey);
                } else {
                    System.err.println("Error - failed to save event: " + task.getException());
                }
            });
        } else {
            System.err.println("Failed to generate a valid key for event.");
        }
    }

    /**
     * Read objects from a given path and cast them to a class.
     *
     * @param path The path to read from
     * @param classType The class to cast to
     * @param callback The callback function
     */
    private <T> void getObjectsFromPath(String path, Class<T> classType,
                                                  Consumer<ArrayList<T>> callback) {
        if(root == null) initializeFB();
        DatabaseReference tripsRef = root.child(path);


        ArrayList<T> list = new ArrayList<>();
        tripsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot tripSnapshot : dataSnapshot.getChildren()) {
                    list.add(tripSnapshot.getValue(classType));
                }

                callback.accept(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Add an event to an existing trip in the Firebase database.
     *
     * @param tripId The ID of the trip to update.
     * @param event The event to add to the trip.
     * @param callback A callback to handle success or failure.
     */
    /**
     * Add an event to a specific trip in Firebase.
     *
     * @param tripId The ID of the trip to update.
     * @param event The event to add to the trip.
     * @param callback A callback to handle success or failure.
     */

    /**
     * This function will create a new trip in the DB.
     *
     * @param trip The trip to be added
     */
    protected void createTrip(Trip trip) {
        pushObject("trips", trip);
    }

    /**
     * Add an event to a trip identified by its name in the Firebase database.
     *
     * @param tripName The name of the trip to update
     * @param event The event to add to the trip
     */
    protected void addEventToTrip(String tripName, Event event) {
        if (root == null) {
            initializeFB();
        }
        DatabaseReference tripRef = (DatabaseReference) root.child("trips").orderByChild("name").equalTo(tripName);

        tripRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot tripSnapshot : snapshot.getChildren()) {
                        Trip trip = tripSnapshot.getValue(Trip.class);
                        if (trip != null) {
                            if (trip.getEvents() == null) {
                                trip.setEvents(new ArrayList<>());
                            }
                            trip.addEvent(event);

                            tripSnapshot.getRef().setValue(trip).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    System.out.println("Event added successfully to trip: " + tripName);
                                } else {
                                    System.err.println("Failed to add event to trip: " + tripName);
                                }
                            });
                            return;
                        }
                    }
                } else {
                    System.err.println("Trip not found: " + tripName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Database error: " + error.getMessage());
            }
        });
    }

    /**
     * Return all trips from the DB.
     *
     * @param callback When read is read, call this function
     */
    protected void getTrips(Consumer<ArrayList<Trip>> callback) {
        getObjectsFromPath("trips", Trip.class, callback);
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
    protected void getEvents(Consumer<ArrayList<Event>> callback) {
        getObjectsFromPath("events", Event.class, callback);
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
     *
     * @param callback When read is read, call this function.
     */
    protected void getAllBusinessOwnerEvents(Consumer<ArrayList<Event>> callback) {
        getObjectsFromPath("eventsBO", Event.class, callback);
    }

    private Map<String, Object> serializeEvent(Event event) {
        Map<String, Object> eventMap = new HashMap<>();
        eventMap.put("title", event.getTitle());
        eventMap.put("factors", event.getFactors());
        eventMap.put("startTime", event.getStartTime() != null ? event.getStartTime().getTime() : null);
        eventMap.put("endTime", event.getEndTime() != null ? event.getEndTime().getTime() : null);
        eventMap.put("description", event.getDescription());
        eventMap.put("location", event.getLocation());
        eventMap.put("imageURL", event.getImageURL());
        return eventMap;
    }


}
