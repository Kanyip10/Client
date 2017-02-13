package com.html5killer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.html5killer.model.User;
import com.html5killer.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



/**
 * Created by KanYip on 8/2/17.
*/

public class TestResultActivity extends AppCompatActivity {

    private TestLibrary mQuestionLibrary = new TestLibrary();
    private TestActivity mTestActivity = new TestActivity();
/*
    private ListView listView;
    private TextView mQuestionView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;
    private Button mButtonChoice4;

    private String mAnswer;
    private int mScore = 0;
    private int mQuestionNumber = 0;
*/
    private TextView mScoreView;
    private String mToken;
    private String mEmail;
    private Button mBtFinish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        mScoreView = (TextView)findViewById(R.id.score);
        mBtFinish = (Button) findViewById(R.id.btn_finish);

        mScoreView.setText("Score = " + mTestActivity.getCountCorrect() + "/" + mTestActivity.getCountQ());
        mBtFinish.setOnClickListener(view -> finishing());


        ListView resultListView = (ListView) findViewById(R.id.answer);

        HashMap<String, String> qna = new HashMap<>();
        /*
        qna.put("1","a");
        qna.put("2","b");
        qna.put("3","c");
        qna.put("4","d");
        */

        for (int i = 0; i < mTestActivity.getCountQ(); i++) {
            if (mQuestionLibrary.getCorrectAnswer(i) == mTestActivity.getuAnswer(i)) {
                qna.put(mQuestionLibrary.getQuestion(i), "Correct Ans: " +
                        mQuestionLibrary.getCorrectAnswer(i) + "\nYour Ans: "
                        + mTestActivity.getuAnswer(i) + "  \u2705");
            } else{
                qna.put(mQuestionLibrary.getQuestion(i), "Correct Ans: " +
                        mQuestionLibrary.getCorrectAnswer(i) + "\nYour Ans: "
                        + mTestActivity.getuAnswer(i)+ "  \u274c");
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

        initSharedPreferences();

        //updateExp(User user);
    }



    private void initSharedPreferences(){
        Bundle bundle = getIntent().getExtras();
        mToken = bundle.getString(Constants.TOKEN);
        mEmail = bundle.getString(Constants.EMAIL);

    }
    /*
    private void updateExp(){
        user.setExperience(user.getExperience()+ 5 * mTestActivity.getCountCorrect());
    }
*/
    private void finishing(){

        Intent intent = new Intent(this, ProfileActivity.class);
        ProfileActivity test1 = new ProfileActivity();



        Bundle bundle = new Bundle();
        bundle.putString(Constants.EMAIL, mEmail);
        bundle.putString(Constants.TOKEN,mToken);
        intent.putExtras(bundle);
        startActivity(intent);

    }


}
