
/**
 * Created by KanYip on 21/1/17.
 */


package com.html5killer;
import android.support.v7.app.AppCompatActivity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.html5killer.model.Response;
import com.html5killer.model.ResponseTest;
import com.html5killer.model.Quiz;
import com.html5killer.network.NetworkUtil;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class SetTestActivity extends AppCompatActivity {

    public static final String TAG = SetTestActivity.class.getSimpleName();

    private EditText mEtTestName;
    private EditText mEtQuestion;
    private EditText mEtChoice1;
    private EditText mEtChoice2;
    private EditText mEtChoice3;
    private EditText mEtChoice4;
    private EditText mEtAnswer;
    private TextInputLayout mTiTestName;
    private TextInputLayout mTiQuestion;
    private TextInputLayout mTiChoice1;
    private TextInputLayout mTiChoice2;
    private TextInputLayout mTiChoice3;
    private TextInputLayout mTiChoice4;
    private TextInputLayout mTiAnswer;

    private Button   mBtNext;
    private Button   mBtFinish;


    private CompositeSubscription mSubscriptions;

    private String name;
    private String[] question;
    private String[][] choice;
    private String[] answer;
    private int count;

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register,container,false);
        mSubscriptions = new CompositeSubscription();
        initViews(view);
        return view;
    }

    private void initViews(View v) {
        mEtTestName = (EditText) v.findViewById(R.id.et_testName);
        mEtQuestion = (EditText) v.findViewById(R.id.et_question);
        mEtChoice1 = (EditText) v.findViewById(R.id.et_choice1);
        mEtChoice2 = (EditText) v.findViewById(R.id.et_choice2);
        mEtChoice3 = (EditText) v.findViewById(R.id.et_choice3);
        mEtChoice4 = (EditText) v.findViewById(R.id.et_choice4);
        mEtAnswer = (EditText) v.findViewById(R.id.et_answer);
        mTiTestName = (TextInputLayout) v.findViewById(R.id.ti_testName);
        mTiQuestion = (TextInputLayout) v.findViewById(R.id.ti_question);
        mTiChoice1 = (TextInputLayout) v.findViewById(R.id.ti_choice1);
        mTiChoice2 = (TextInputLayout) v.findViewById(R.id.ti_choice2);
        mTiChoice3 = (TextInputLayout) v.findViewById(R.id.ti_choice3);
        mTiChoice4 = (TextInputLayout) v.findViewById(R.id.ti_choice4);
        mTiAnswer = (TextInputLayout) v.findViewById(R.id.ti_answer);
        mBtNext = (Button) v.findViewById(R.id.btn_next);
        mBtFinish = (Button) v.findViewById(R.id.btn_finish);

        mBtNext.setOnClickListener(view -> next());
        mBtFinish.setOnClickListener(view -> finished());
    }

    //Handle the operation of the button 'Next'
    private void next() {

        //Get the question
        for(int i = 0; i < count; i++){
            if (question[i] == null) {
                question[i] = mEtQuestion.getText().toString();
            }
        }

        //Get the choices
        String[] tempChoices = {};
        tempChoices[0] = mEtChoice1.getText().toString();
        tempChoices[1] = mEtChoice2.getText().toString();
        tempChoices[2] = mEtChoice3.getText().toString();
        tempChoices[3] = mEtChoice4.getText().toString();

        for(int i = 0; i < count; i++){
            if (choice[i][0] == null) {
                for (int x = 0; x < 4; x++) {
                    choice[i][x] = tempChoices[x];
                }
            }
        }


        //Get the correct answer
        for(int i = 0; i < count; i++){
            if (answer[i] == null) {
                answer[i] = mEtAnswer.getText().toString();
            }
        }

        int err = 0;
/*
        if (!validateFields(name)) {

            err++;
            mTiName.setError("Name should not be empty !");
        }

        if (!validateEmail(email)) {

            err++;
            mTiEmail.setError("Email should be valid !");
        }

        if (!validateFields(password)) {

            err++;
            mTiPassword.setError("Password should not be empty !");
        }
*/
    }

    //Handle the operation of button 'Finish'
    private void finished(){

        //Get the test name
        name = mEtTestName.getText().toString();

        Quiz quiz = new Quiz();
        quiz.setName(name);
        quiz.setQuestion(question);
       // quiz.setChoice(choice);
        quiz.setAnswer(answer);

        setTestProcess(quiz);

    }

    /*
    private void setError() {

        mTiName.setError(null);
        mTiEmail.setError(null);
        mTiPassword.setError(null);
    }
    */

    private void setTestProcess(Quiz quiz) {

        mSubscriptions.add(NetworkUtil.getRetrofit().setQuiz(quiz)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(Response response) {

        //mProgressbar.setVisibility(View.GONE);
        showSnackBarMessage(response.getMessage());
    }

    private void handleError(Throwable error) {

        //mProgressbar.setVisibility(View.GONE);

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

        /*if (getView() != null) {

        Snackbar.make(getView(),message,Snackbar.LENGTH_SHORT).show();
        }
        */
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }
}





