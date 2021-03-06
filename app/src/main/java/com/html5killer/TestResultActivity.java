package com.html5killer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.html5killer.fragments.ChangePasswordDialog;
import com.html5killer.model.Response;
import com.html5killer.model.User;
import com.html5killer.network.NetworkUtil;
import com.html5killer.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by KanYip on 8/2/17.
*/

public class TestResultActivity extends AppCompatActivity {

    private TestLibrary mQuestionLibrary = new TestLibrary();
    private TestLibrary1 mQuestionLibrary1 = new TestLibrary1();
    private TestLibrary2 mQuestionLibrary2 = new TestLibrary2();
    private TestActivity mTestActivity = new TestActivity();

    private TextView mScoreView;
    private TextView mExp;
    private String mToken;
    private String mEmail;
    private Button mBtFinish;
    private String tutorial;


    private int exp = 0;

    private CompositeSubscription mSubscriptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        mSubscriptions = new CompositeSubscription();
        initSharedPreferences();
        loadProfile();
        System.out.println(mEmail);
        System.out.println(mToken);

        mScoreView = (TextView)findViewById(R.id.score);
        mExp = (TextView)findViewById(R.id.exp);
        mBtFinish = (Button) findViewById(R.id.btn_finish);

        mScoreView.setText("Score = " + mTestActivity.getCountCorrect() + "/" + mTestActivity.getCountQ());

        mBtFinish.setOnClickListener(view -> finishing());



        ListView resultListView = (ListView) findViewById(R.id.answer);

        HashMap<String, String> qna = new HashMap<>();


        if (tutorial.equals("3")) {
            for (int i = 0; i < mTestActivity.getCountQ(); i++) {
                if (mQuestionLibrary.getCorrectAnswer(i) == mTestActivity.getuAnswer(i)) {
                    qna.put(mQuestionLibrary.getQuestion(i), "Correct Ans: " +
                            mQuestionLibrary.getCorrectAnswer(i) + "\nYour Ans: "
                            + mTestActivity.getuAnswer(i) + "  \u2705");
                } else {
                    qna.put(mQuestionLibrary.getQuestion(i), "Correct Ans: " +
                            mQuestionLibrary.getCorrectAnswer(i) + "\nYour Ans: "
                            + mTestActivity.getuAnswer(i) + "  \u274c");
                }
            }
        }else if (tutorial.equals("1")){
            for (int i = 0; i < mTestActivity.getCountQ(); i++) {
                if (mQuestionLibrary1.getCorrectAnswer(i) == mTestActivity.getuAnswer(i)) {
                    qna.put(mQuestionLibrary1.getQuestion(i), "Correct Ans: " +
                            mQuestionLibrary1.getCorrectAnswer(i) + "\nYour Ans: "
                            + mTestActivity.getuAnswer(i) + "  \u2705");
                } else {
                    qna.put(mQuestionLibrary1.getQuestion(i), "Correct Ans: " +
                            mQuestionLibrary1.getCorrectAnswer(i) + "\nYour Ans: "
                            + mTestActivity.getuAnswer(i) + "  \u274c");
                }
            }
        } else if (tutorial.equals("2")){
            for (int i = 0; i < mTestActivity.getCountQ(); i++) {
                if (mQuestionLibrary2.getCorrectAnswer(i) == mTestActivity.getuAnswer(i)) {
                    qna.put(mQuestionLibrary2.getQuestion(i), "Correct Ans: " +
                            mQuestionLibrary2.getCorrectAnswer(i) + "\nYour Ans: "
                            + mTestActivity.getuAnswer(i) + "  \u2705");
                } else {
                    qna.put(mQuestionLibrary2.getQuestion(i), "Correct Ans: " +
                            mQuestionLibrary2.getCorrectAnswer(i) + "\nYour Ans: "
                            + mTestActivity.getuAnswer(i) + "  \u274c");
                }
            }
        }

        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.test_result_row,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.Questions, R.id.Answers});

        Iterator it = qna.entrySet().iterator();
        while (it.hasNext()) {
            HashMap<String, String> resultMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultMap.put("First Line", pair.getKey().toString());
            resultMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultMap);
        }

        resultListView.setAdapter(adapter);
        updateExp();

    }


    private void initSharedPreferences(){
        Bundle bundle = getIntent().getExtras();
        mToken = bundle.getString(Constants.TOKEN);
        mEmail = bundle.getString(Constants.EMAIL);
        tutorial = bundle.getString("tutorial");

    }

    private void updateExp(){
        User user = new User();
        if(mTestActivity.getCountCorrect() == 4){
            user.setNewExp(50);
            if(tutorial.equals("3")) {
                changeExp3(user);
            } else if(tutorial.equals("1")){
                changeExp(user);
            } else if(tutorial.equals("2")){
                changeExp2(user);
            }
        }

    }



    private void changeExp3(User user) {

        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).changeExp3(mEmail,user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::Response,this::handleError));
    }

    private void changeExp(User user) {

        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).changeExp(mEmail,user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::Response,this::handleError));
    }

    private void changeExp2(User user) {

        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).changeExp2(mEmail,user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::Response,this::handleError));
    }

    private void Response(Response response) {

    }





    private void finishing(){

        if(mEmail.toString().charAt(0) == 's'){
            Intent intent = new Intent(this, ProfileActivity.class);
            //ProfileActivity test1 = new ProfileActivity();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.EMAIL, mEmail);
            bundle.putString(Constants.TOKEN,mToken);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, Teacher_ProfileActivity.class);
            //Teacher_ProfileActivity test1 = new Teacher_ProfileActivity();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.EMAIL, mEmail);
            bundle.putString(Constants.TOKEN,mToken);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }


    }

    private void loadProfile() {

        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).getProfile(mEmail)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));



    }

   private void handleResponse(User user) {


    }

    private void handleError(Throwable error) {



        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }
    }

    private void showSnackBarMessage(String message) {

        Snackbar.make(findViewById(R.id.testResult),message,Snackbar.LENGTH_SHORT).show();

    }




}
