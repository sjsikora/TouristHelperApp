package com.example.touristhelperapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class EventIcon extends Fragment {

    private static final String ARG_EVENT = "event";

    private Event event;

    public EventIcon() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView eventIcon = view.findViewById(R.id.eventImage);
        TextView eventText = view.findViewById(R.id.subtitle);

        eventText.setText(event.getTitle()); // Set text directly
        DateHelper.formatDateWithSuffix(event.getStartTime());

        // We wrap downloading the image in a thread to avoid blocking the UI.
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(event.getImageURL());
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    // Once we get here, link back into the main thread
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            eventIcon.setImageBitmap(bmp);
                        }
                    });
                } catch (IOException ignored) {}
            }
        }).start();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            event = getArguments().getParcelable(ARG_EVENT, Event.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_icon, container, false);
    }
}