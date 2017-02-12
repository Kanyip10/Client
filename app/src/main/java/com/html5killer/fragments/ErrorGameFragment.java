package com.html5killer.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.html5killer.R;

/**
 * Created by Ashley Wong on 12/2/2017.
 */

public class ErrorGameFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game_page, container, false);


        return rootView;
    }
}
