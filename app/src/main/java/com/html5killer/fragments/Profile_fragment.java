package com.html5killer.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.html5killer.ProfileActivity;
import com.html5killer.R;
import com.html5killer.ReferenceListActivity;
import com.html5killer.TestActivity;
import com.html5killer.model.Response;
import com.html5killer.model.User;
import com.html5killer.network.NetworkUtil;
import com.html5killer.utils.Constants;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Ashley Wong on 16/2/2017.
 */

public class Profile_fragment extends Fragment implements ChangePasswordDialog.Listener{

    public static final String TAG = Profile_fragment.class.getSimpleName();

    private TextView mTvEmail;
    private TextView nName;
    private TextView nEmail;
    private TextView mTvName;
    private TextView mTvDate;
    private TextView mExp;
    private TextView mExp2;
    private TextView mExp3;
    private TextView mExp4;
    private TextView mExp5;
    private Button mBtChangePassword;
    private Button mBtLogout;
    private Button mBtTest;
    private Button mBtReferenceList;

    private ProgressBar mProgressbar;
    private ProgressBar mProgressbar2;
    private ProgressBar mProgressbar3;
    private ProgressBar mProgressbar4;
    private ProgressBar mProgressbar5;

    private SharedPreferences mSharedPreferences;
    private String mToken;
    private String mEmail;

    private CompositeSubscription mSubscriptions;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main,container,false);
        mSubscriptions = new CompositeSubscription();
        initViews(view);

        getData();
        loadProfile();


        return view;
    }

    private void initViews(View v) {


        //mTvEmail = (TextView) v.findViewById(R.id.tv_email);
        //mTvDate = (TextView) v.findViewById(R.id.tv_date);
        mTvName = (TextView) v.findViewById(R.id.name);
        mExp = (TextView) v.findViewById(R.id.tv_exp);
        mExp2 = (TextView) v.findViewById(R.id.tv_exp2);
        mExp3 = (TextView) v.findViewById(R.id.tv_exp3);
        //mExp4 = (TextView) v.findViewById(R.id.tv_exp4);
        //mExp5 = (TextView) v.findViewById(R.id.tv_exp5);
        mProgressbar = (ProgressBar) v.findViewById(R.id.progressBar);
        mProgressbar2 = (ProgressBar) v.findViewById(R.id.progressBar2);
        mProgressbar3 = (ProgressBar) v.findViewById(R.id.progressBar3);
        //mProgressbar4 = (ProgressBar) v.findViewById(R.id.progressBar4);
        //mProgressbar5 = (ProgressBar) v.findViewById(R.id.progressBar5);


      /*  mBtChangePassword = (Button) v.findViewById(R.id.btn_change_password);
        mBtLogout = (Button) v.findViewById(R.id.btn_logout);
        mBtTest = (Button) v.findViewById(R.id.btn_test);
        mBtReferenceList = (Button) v.findViewById(R.id.btn_reference_list);
        mProgressbar = (ProgressBar) v.findViewById(R.id.progress);

       // nName = (TextView) (v.findViewById(R.id.nName));
       // nEmail = (TextView) (v.findViewById(R.id.nEmail));


        mBtChangePassword.setOnClickListener(view -> showDialog());
       // mBtLogout.setOnClickListener(view -> logout());
        mBtTest.setOnClickListener(view -> test());
        mBtReferenceList.setOnClickListener(view -> referenceList());*/
    }


    private void getData() {

        Bundle bundle = getArguments();

        mToken = bundle.getString(Constants.TOKEN);
        mEmail = bundle.getString(Constants.EMAIL);
    }
   /* private void logout() {

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.EMAIL, "");
        editor.putString(Constants.TOKEN, "");
        editor.apply();
        finish();
    }*/





    private void referenceList() {
        Intent intent = new Intent(getActivity(), ReferenceListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EMAIL, mEmail);
        bundle.putString(Constants.TOKEN, mToken);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    private void loadProfile() {

        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).getProfile(mEmail)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(User user) {

        //mTvEmail.setText(user.getEmail());
        //mTvDate.setText(user.getCreated_at());
        mTvName.setText("User: " + user.getName());
        mExp.setText("Exp.: " + user.getExperience());
        mExp2.setText("Exp.: " + user.getExperience2());
        mExp3.setText("Exp.: " + user.getExperience3());
        //mExp4.setText("Exp.: " + user.getExperience4());
        //mExp5.setText("Exp.: " + user.getExperience5());
        mProgressbar.setProgress(user.getExperience());
        mProgressbar2.setProgress(user.getExperience2());
        mProgressbar3.setProgress(user.getExperience3());
        //mProgressbar4.setProgress(user.getExperience4());
        //smProgressbar5.setProgress(user.getExperience5());
    }

    private void handleError(Throwable error) {


        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody, Response.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }
    }

    private void showSnackBarMessage(String message) {

        if (getView() != null) {

            Snackbar.make(getView(),message,Snackbar.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    @Override
    public void onPasswordChanged() {

        showSnackBarMessage("Password Changed Successfully !");
    }


}




