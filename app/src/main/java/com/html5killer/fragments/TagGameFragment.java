package com.html5killer.fragments;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
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
 * Created by Ashley Wong on 12/2/2017.
 */

public class TagGameFragment extends Fragment {

    private CompositeSubscription mSubscriptions;

    private String mToken;
    private String mEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_game_page, container, false);
        mSubscriptions = new CompositeSubscription();
        getData();
        TextView game = (TextView) rootView.findViewById(R.id.textView3);
        TextView game_rule = (TextView) rootView.findViewById(R.id.textView2);
        Button StartButton = (Button) rootView.findViewById(R.id.button);

        game.setText(getString(R.string.game1));
        game_rule.setText(getString(R.string.game1_rule));
        StartButton.setOnClickListener(View -> tagGame() );

        return rootView;
    }


    private void getData() {

        Bundle bundle = getArguments();

        mToken = bundle.getString(Constants.TOKEN);
        mEmail = bundle.getString(Constants.EMAIL);
    }

    private void tagGame(){
        Intent intent = new Intent(getActivity(), GameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EMAIL, mEmail);
        bundle.putString(Constants.TOKEN,mToken);
        intent.putExtras(bundle);
        startActivity(intent);

    }

}