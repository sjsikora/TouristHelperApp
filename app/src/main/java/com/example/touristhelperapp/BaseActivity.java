package com.example.touristhelperapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

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
I      */
    private void pushObject(String path, Object object) {
        if(root == null) initializeFB();

        // Put "cursor" on /<name>
        DatabaseReference tripsRef = root.child(path);

        // Push object into the DB
        tripsRef.push().setValue(object);
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
     * Check if an event is in a trip.
     * @param tripName The name of the trip to check
     * @param eventName The name of the event to check
     * @param callback The callback function. Will give a boolean.
     */
    protected void isEventInTrip(String tripName, String eventName, Consumer<Boolean> callback) {
        getAllEventsFromTripName(tripName, (events) -> {
            callback.accept(isEventInArrayList(events, eventName));
        });
    }

    private boolean isEventInArrayList(ArrayList<Event> events, String eventName) {

        // Loop through a verify we are adding a duplicate event
        for(Event e : events) {
            if(e.getTitle().equals(eventName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get all events from a trip.
     * @param tripName The name of the trip to get events from
     * @param callback The callback function. Will give a list of events.
     */
    protected void getAllEventsFromTripName(String tripName, Consumer<ArrayList<Event>> callback) {
        if(root == null) initializeFB();

        DatabaseReference tripRef = root.child("trips");

        Query query = tripRef.orderByChild("name").equalTo((tripName));

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot tripSnap : snapshot.getChildren()) {

                    // Get the key of the trip
                    String tripKey = tripSnap.getKey();
                    assert tripKey != null;

                    // Retrieve all other events
                    DataSnapshot eventsSnapshot = tripSnap.child("events");
                    GenericTypeIndicator<ArrayList<Event>> t
                            = new GenericTypeIndicator<ArrayList<Event>>() {};

                    ArrayList<Event> events = eventsSnapshot.getValue(t);

                    callback.accept(events);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    /**
     * Add an event to a trip, identified by its name, in the Firebase database.
     *
     * @param tripName The name of the trip to update
     * @param event The event to add to the trip
     * @param callback The callback function. Will give error codes
     *                 200 - Successfully added event
     *                 404 - Something Went wrong internally
     *                 409 - Trip does not exist
     *                 410 - Event already in trip.
     */
    protected void addEventToTrip(String tripName, Event event, Consumer<Integer> callback) {
        updateEventInTrip(tripName, event, callback, (events, e) -> {
            if(isEventInArrayList(events, e.getTitle())) {
                callback.accept(410);
                return null;
            } else {
                events.add(e);
            }

            return events;
        });

    }

    /**
     * Remove an event to a trip, identified by its name, in the Firebase database.
     *
     * @param tripName The name of the trip to update
     * @param event The event to add to the trip
     * @param callback The callback function. Will give error codes
     *                 200 - Successfully added event
     *                 404 - Something Went wrong internally
     *                 409 - Trip does not exist
     *                 410 - Event not in the trip
     */
    protected void removeFromTrip(String tripName, Event event, Consumer<Integer> callback) {

        updateEventInTrip(tripName, event, callback, (events, e) -> {

            if(!isEventInArrayList(events, e.getTitle())) {
                callback.accept(410);
                return null;
            } else {

                events.removeIf(ee -> ee.getTitle().equals(e.getTitle()));

                return events;
            }
        });

    }

    /**
     * Add an event to a trip, identified by its name, in the Firebase database.
     *
     * @param tripName The name of the trip to update
     * @param event The event to add to the trip
     * @param callback The callback function. Will give error codes
     *                 200 - Successfully added event
     *                 404 - Something Went wrong internally
     *                 409 - Trip does not exist
     *                 410 - Dependent on caller
     * @param updateFunction A function given a trip arraylist and event gives back an arraylist
     */
    protected void updateEventInTrip(String tripName, Event event,
                                  Consumer<Integer> callback,
                                  BiFunction<ArrayList<Event>, Event, ArrayList<Event>> updateFunction) {
        if(root == null) initializeFB();

        DatabaseReference tripRef = root.child("trips");
        Query query = tripRef.orderByChild("name").equalTo((tripName));

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!snapshot.hasChildren()) {
                    callback.accept(409);
                    return;
                }

                for(DataSnapshot tripSnap : snapshot.getChildren()) {

                    // Get the key of the trip
                    String tripKey = tripSnap.getKey();
                    assert tripKey != null;

                    // Retrieve all other events
                    DataSnapshot eventsSnapshot = tripSnap.child("events");
                    GenericTypeIndicator<ArrayList<Event>> t = new GenericTypeIndicator<ArrayList<Event>>() {};
                    ArrayList<Event> events = eventsSnapshot.getValue(t);

                    if(events == null) events = new ArrayList<>();

                    events = updateFunction.apply(events, event);

                    // If we were given null, that means the callback was run and we should
                    // do no more processing
                    if(events == null) return;

                    // Update
                    HashMap<String, Object> updates = new HashMap<>();

                    updates.put("events", events);

                    // Update the trip in the database
                    tripRef.child(tripKey).updateChildren(updates)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    callback.accept(200);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    callback.accept(404);
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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



}
