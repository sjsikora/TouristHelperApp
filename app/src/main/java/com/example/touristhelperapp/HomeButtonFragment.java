package com.example.touristhelperapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class HomeButtonFragment extends Fragment {

    public HomeButtonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        ImageView home = view.findViewById(R.id.homeButtonImageView);
        home.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Home.class);
            startActivity(intent);
        });
    }

    public static HomeButtonFragment newInstance(String param1, String param2) {
        HomeButtonFragment fragment = new HomeButtonFragment();
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