package com.example.touristhelperapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

public class SearchResultEvent extends Fragment {

    private static final String ARG_EVENT = "event";

    // TODO: Rename and change types of parameters
    private Event event;
    private Bitmap image;

    public SearchResultEvent() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView title = view.findViewById(R.id.title);
        TextView factors = view.findViewById(R.id.factors);
        TextView time = view.findViewById(R.id.time);
        ImageView imageView = view.findViewById(R.id.image);

        title.setText(event.getTitle());
        factors.setText(String.join(", ", event.getFactors()));

        StringBuilder concat = new StringBuilder();
        Date start = event.getStartTime();
        Date end = event.getEndTime();

        concat.append(DateHelper.formatDateWithSuffix(start));
        concat.append(" ");
        concat.append(DateHelper.startTimeToEndTime(start, end));

        time.setText(concat.toString());

        // We wrap downloading the image in a thread to avoid blocking the UI.
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(event.getImageURL());
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    // Once we get here, link back into the main thread
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(image);
                        }
                    });
                } catch (IOException ignored) {}
            }
        }).start();



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), AddEventActivity.class);
                intent.putExtra("event", event);

                if(image != null && image.getByteCount() < 100000) {
                    intent.putExtra("image", image);
                }

                startActivity(intent);

            }
        });

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
        return inflater.inflate(R.layout.fragment_search_result_event, container, false);
    }
}