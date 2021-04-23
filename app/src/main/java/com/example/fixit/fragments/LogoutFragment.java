package com.example.fixit.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fixit.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/*
    PLACEHOLDER FRAGMENT
    AS OF RIGHT NOW, CLICKING THE FRAGMENT WILL POP UP AN EMPTY XML LAYOUT BEFORE LOGGING OFF.
    A MORE ELEGANT AND FASTER METHOD WOULD BE TO HAVE THE LOGIN BE A FRAGMENT ITSELF.
    THAT WAY, THERE WON'T BE A TRANSITION XML LAYOUT BEFORE LOGGING OFF.
    BUT THIS WORKS FOR NOW.
 */
public class LogoutFragment extends Fragment {



    public LogoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }
}