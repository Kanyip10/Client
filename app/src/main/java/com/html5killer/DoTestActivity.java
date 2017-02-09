package com.html5killer;

/**
 * Created by KanYip on 20/1/17.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.html5killer.model.Quiz;

public class DoTestActivity extends AppCompatActivity {

    private Quiz mQuestionLibrary = new Quiz();

    private TextView mScoreView;
    private TextView mQuestionView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;
    private Button mButtonChoice4;

    private String mAnswer;
    private int mScore = 0;
    private int mQuestionNumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_test);
        initViews();
    }

    private void initViews() {

        mScoreView = (TextView) findViewById(R.id.score);
        mQuestionView = (TextView) findViewById(R.id.question);
        mButtonChoice1 = (Button) findViewById(R.id.choice1);
        mButtonChoice2 = (Button) findViewById(R.id.choice2);
        mButtonChoice3 = (Button) findViewById(R.id.choice3);
        mButtonChoice4 = (Button) findViewById(R.id.choice4);

        mButtonChoice1.setOnClickListener(view -> choice1OnClick());
        mButtonChoice2.setOnClickListener(view -> choice2OnClick());
        mButtonChoice3.setOnClickListener(view -> choice3OnClick());
        mButtonChoice4.setOnClickListener(view -> choice4OnClick());
        updateQuestion();
    }
    //Start of Button Listener for Button1

    public void choice1OnClick(){
        //My logic for Button goes in here

        if (mButtonChoice1.getText() == mAnswer){
            mScore = mScore + 1;
            updateScore(mScore);
            updateQuestion();
            //This line of code is optional
            Toast.makeText(DoTestActivity.this, "correct", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(DoTestActivity.this, "wrong", Toast.LENGTH_SHORT).show();
            updateQuestion();
        }
    }

    //End of Button Listener for Button1

    //Start of Button Listener for Button2

    public void choice2OnClick(){
        //My logic for Button goes in here

        if (mButtonChoice2.getText() == mAnswer){
            mScore = mScore + 1;
            updateScore(mScore);
            updateQuestion();
            //This line of code is optional
            Toast.makeText(DoTestActivity.this, "correct", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(DoTestActivity.this, "wrong", Toast.LENGTH_SHORT).show();
            updateQuestion();
        }
    }

    //End of Button Listener for Button2


    //Start of Button Listener for Button3

    public void choice3OnClick(){
        //My logic for Button goes in here

        if (mButtonChoice3.getText() == mAnswer){
            mScore = mScore + 1;
            updateScore(mScore);
            updateQuestion();
            //This line of code is optional
            Toast.makeText(DoTestActivity.this, "correct", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(DoTestActivity.this, "wrong", Toast.LENGTH_SHORT).show();
            updateQuestion();
        }
    }


    //End of Button Listener for Button3

    //Start of Button Listener for Button4

    public void choice4OnClick(){
        //My logic for Button goes in here

        if (mButtonChoice4.getText() == mAnswer){
            mScore = mScore + 1;
            updateScore(mScore);
            updateQuestion();
            //This line of code is optional
            Toast.makeText(DoTestActivity.this, "correct", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(DoTestActivity.this, "wrong", Toast.LENGTH_SHORT).show();
            updateQuestion();
        }
    }

    //End of Button Listener for Button3 4

    private void updateQuestion(){
        mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
        mButtonChoice1.setText(mQuestionLibrary.getChoice1(mQuestionNumber));
        mButtonChoice2.setText(mQuestionLibrary.getChoice2(mQuestionNumber));
        mButtonChoice3.setText(mQuestionLibrary.getChoice3(mQuestionNumber));
        mButtonChoice4.setText(mQuestionLibrary.getChoice4(mQuestionNumber));

        mAnswer = mQuestionLibrary.getAnswer(mQuestionNumber);
        mQuestionNumber++;
    }


    private void updateScore(int point) {
        mScoreView.setText("" + mScore);
    }
}

