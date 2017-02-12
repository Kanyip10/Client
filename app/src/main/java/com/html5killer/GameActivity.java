package com.html5killer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.html5killer.utils.Constants;

import java.util.ArrayList;

import rx.subscriptions.CompositeSubscription;


/**
 * Created by Ashley Wong on 12/2/2017.
 */

public class GameActivity extends AppCompatActivity{

    public static final String TAG = GameActivity.class.getSimpleName();
    private SharedPreferences mSharedPreferences;
    private String mToken;
    private String mEmail;

    private CompositeSubscription mSubscriptions;

    ArrayList<String> submitAnswer = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_game);

        mSubscriptions = new CompositeSubscription();
        initSharedPreferences();

        TextView timer = (TextView) findViewById(R.id.timer);
        EditText answer = (EditText) findViewById(R.id.tagName);
        Button submit = (Button) findViewById(R.id.submit);

        new CountDownTimer(300000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText((millisUntilFinished / 60000)+":"+(millisUntilFinished % 60000 / 1000));
            }

            public void onFinish() {
                timer.setText("done!");
                submit.setVisibility(View.INVISIBLE);
            }
        }.start();






    }

    private void initSharedPreferences() {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mToken = mSharedPreferences.getString(Constants.TOKEN,"");
        mEmail = mSharedPreferences.getString(Constants.EMAIL,"");
    }




}

