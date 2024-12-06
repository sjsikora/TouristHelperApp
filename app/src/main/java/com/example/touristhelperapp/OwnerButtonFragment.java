package com.example.touristhelperapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class OwnerButtonFragment extends Fragment {

    public OwnerButtonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        ImageView home = view.findViewById(R.id.homeButtonImageView);
        home.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), OwnerEvent.class);
            startActivity(intent);
        });
    }

    public static OwnerButtonFragment newInstance(String param1, String param2) {
        OwnerButtonFragment fragment = new OwnerButtonFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_button, container, false);
    }


}