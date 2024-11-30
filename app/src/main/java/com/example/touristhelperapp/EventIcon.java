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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventIcon#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventIcon extends Fragment {

    private static final String ARG_PHOTOLINK = "photoLink";
    private static final String ARG_EVENTNAME = "eventName";

    private String photoLink;
    private String eventName;

    public EventIcon() {
        // Required empty public constructor
    }



    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView eventIcon = view.findViewById(R.id.eventImage);
        TextView eventText = view.findViewById(R.id.subtitle);

        eventText.setText(eventName); // Set text directly


        // We wrap downloading the image in a thread to avoid blocking the UI.

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(photoLink);
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventIcon.
     */
    // TODO: Rename and change types and number of parameters
    public static EventIcon newInstance(String param1, String param2) {
        EventIcon fragment = new EventIcon();
        Bundle args = new Bundle();
        args.putString(ARG_PHOTOLINK, param1);
        args.putString(ARG_EVENTNAME, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            photoLink = getArguments().getString(ARG_PHOTOLINK);
            eventName = getArguments().getString(ARG_EVENTNAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_icon, container, false);
    }
}