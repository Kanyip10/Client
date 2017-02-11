package com.html5killer;

/**
 * Created by KanYip on 2/2/17.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.html5killer.utils.Constants;

import junit.framework.TestResult;

public class TestActivity extends AppCompatActivity {

    private TestLibrary mQuestionLibrary = new TestLibrary();

    private TextView mScoreView;
    private TextView mQuestionView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;
    private Button mButtonChoice4;

    private String mAnswer;
    private int mScore = 0;
    private int mQuestionNumber = 0;

    private String mToken;
    private String mEmail;

    private  static String[] uAnswer = {"1", "2", "3", "4"};
    private static int countQ = 0;

    private static String[] test1={};
    private static int count = 0;

    private void initSharedPreferences(){
        Bundle bundle = getIntent().getExtras();
        mToken = bundle.getString(Constants.TOKEN);
        mEmail = bundle.getString(Constants.EMAIL);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_test);
        initSharedPreferences();
        mScoreView = (TextView)findViewById(R.id.score);
        mQuestionView = (TextView)findViewById(R.id.question);
        mButtonChoice1 = (Button)findViewById(R.id.choice1);
        mButtonChoice2 = (Button)findViewById(R.id.choice2);
        mButtonChoice3 = (Button)findViewById(R.id.choice3);
        mButtonChoice4 = (Button)findViewById(R.id.choice4);

        updateQuestion();

        //Start of Button Listener for Button1
        mButtonChoice1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


                if (mButtonChoice1.getText() == mAnswer){
                    mScore = mScore + 1;
                    uAnswer[countQ] = (String)mButtonChoice1.getText();
                    countQ++;
                    updateScore(mScore);
                    updateQuestion();

                    Toast.makeText(TestActivity.this, "correct", Toast.LENGTH_SHORT).show();

                }else {
                    uAnswer[countQ] = (String)mButtonChoice1.getText();
                    countQ++;
                    Toast.makeText(TestActivity.this, "wrong", Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }
            }
        });

        //End of Button Listener for Button1

        //Start of Button Listener for Button2
        mButtonChoice2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


                if (mButtonChoice2.getText() == mAnswer){
                    mScore = mScore + 1;
                    uAnswer[countQ] = (String)mButtonChoice2.getText();
                    countQ++;
                    updateScore(mScore);
                    updateQuestion();

                    Toast.makeText(TestActivity.this, "correct", Toast.LENGTH_SHORT).show();

                }else {
                    uAnswer[countQ] = (String)mButtonChoice2.getText();
                    countQ++;
                    Toast.makeText(TestActivity.this, "wrong", Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }
            }
        });

        //End of Button Listener for Button2


        //Start of Button Listener for Button3
        mButtonChoice3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


                if (mButtonChoice3.getText() == mAnswer){
                    mScore = mScore + 1;
                    uAnswer[countQ] = (String)mButtonChoice3.getText();
                    countQ++;
                    updateScore(mScore);
                    updateQuestion();

                    Toast.makeText(TestActivity.this, "correct", Toast.LENGTH_SHORT).show();

                }else {
                    uAnswer[countQ] = (String)mButtonChoice3.getText();
                    countQ++;
                    Toast.makeText(TestActivity.this, "wrong", Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }
            }
        });

        //End of Button Listener for Button3

        //Start of Button Listener for Button4
        mButtonChoice4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


                if (mButtonChoice4.getText() == mAnswer){
                    mScore = mScore + 1;
                    uAnswer[countQ] = (String)mButtonChoice4.getText();
                    countQ++;
                    updateScore(mScore);
                    updateQuestion();
                    Toast.makeText(TestActivity.this, "correct", Toast.LENGTH_SHORT).show();

                }else {
                    uAnswer[countQ] = (String)mButtonChoice4.getText();
                    countQ++;
                    Toast.makeText(TestActivity.this, "wrong", Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }
            }
        });

        //End of Button Listener for Button4




    }

    private void updateQuestion(){
        if(mQuestionLibrary.getQuestion(mQuestionNumber) == null){
            test1 = uAnswer;
            count = countQ;
            testResult();
        } else {
            mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
            mButtonChoice1.setText(mQuestionLibrary.getChoice1(mQuestionNumber));
            mButtonChoice2.setText(mQuestionLibrary.getChoice2(mQuestionNumber));
            mButtonChoice3.setText(mQuestionLibrary.getChoice3(mQuestionNumber));
            mButtonChoice4.setText(mQuestionLibrary.getChoice4(mQuestionNumber));

            mAnswer = mQuestionLibrary.getCorrectAnswer(mQuestionNumber);
            mQuestionNumber++;

        }
    }


    private void updateScore(int point) {
        mScoreView.setText("" + mScore);
    }


    private void testResult(){
        Intent intent = new Intent(this, TestResultActivity.class);
        TestResultActivity test1 = new TestResultActivity();

        Bundle bundle = new Bundle();
        bundle.putString(Constants.EMAIL, mEmail);
        bundle.putString(Constants.TOKEN,mToken);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public String getuAnswer(int a){
        String answer = uAnswer[a];
        return answer;
    }

    public int getCountQ(){
        return countQ;
    }

    public String[] getArray(){return uAnswer;}
}
