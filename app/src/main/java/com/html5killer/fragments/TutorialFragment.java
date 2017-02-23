package com.html5killer.fragments;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.html5killer.GameActivity;
import com.html5killer.R;
import com.html5killer.utils.Constants;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Ashley Wong on 19/2/2017.
 */

public class TutorialFragment  extends Fragment{

    private CompositeSubscription mSubscriptions;

    private String mToken;
    private String mEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_game_page, container, false);
        mSubscriptions = new CompositeSubscription();
        getData();


        return rootView;
    }


    private void getData() {

        Bundle bundle = getArguments();

        mToken = bundle.getString(Constants.TOKEN);
        mEmail = bundle.getString(Constants.EMAIL);
    }



}
