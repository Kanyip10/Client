package com.html5killer.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.html5killer.GameActivity;
import com.html5killer.HomeActivity;
import com.html5killer.R;
import com.html5killer.utils.Constants;

/**
 * Created by Ashley Wong on 12/2/2017.
 */

public class ErrorGameFragment extends Fragment{

    private String mToken;
    private String mEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game_page, container, false);
        getData();
        Log.i("TAG", mEmail+ "    " + mToken);
        Button StartButton = (Button) rootView.findViewById(R.id.button);

        StartButton.setOnClickListener(View -> errorGame() );
        return rootView;
    }

    private void getData() {

        Bundle bundle = getArguments();

        mToken = bundle.getString(Constants.TOKEN);
        mEmail = bundle.getString(Constants.EMAIL);
    }

    private void errorGame(){
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EMAIL, mEmail);
        bundle.putString(Constants.TOKEN,mToken);
        intent.putExtras(bundle);
        startActivity(intent);

    }

}
