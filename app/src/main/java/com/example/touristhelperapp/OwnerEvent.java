package com.example.touristhelperapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;

public class OwnerEvent extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_owner_event);


    }
}
