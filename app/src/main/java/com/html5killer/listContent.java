package com.html5killer;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.html5killer.model.ReferenceList;
import com.html5killer.model.Response;
import com.html5killer.network.NetworkUtil;
import com.html5killer.utils.Constants;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Ashley Wong on 13/2/2017.
 */

public class listContent extends AppCompatActivity {
    public static final String TAG = ReferenceListActivity.class.getSimpleName();


    private TextView dname;
    private TextView lname;
    private TextView ddescription;
    private TextView lddescription;
    private TextView dexample;
    private TextView lexample;


    private TextView mTvMessage;


    private CompositeSubscription mSubscriptions;
    private String mToken;
    private String mEmail;

    private String listname;

    private WebView editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_content);
        mSubscriptions = new CompositeSubscription();
        initSharedPreferences();

        dname = (TextView) findViewById(R.id.dname);
        lname = (TextView) findViewById(R.id.lname);
        ddescription = (TextView) findViewById(R.id.ddescrition);
        lddescription = (TextView) findViewById(R.id.ldescrition);
        dexample = (TextView) findViewById(R.id.dexample);
        lexample = (TextView) findViewById(R.id.lexample);
        sorting(listname);

        editor = (WebView) findViewById(R.id.webView);

        editor.getSettings().setJavaScriptEnabled(true);

        //editor.setWebViewClient(new WebViewClient());

        editor.setWebChromeClient(new WebChromeClient());

        editor.loadUrl("file:///android_asset/tryit_editor_mirror/index.html");



    }

    private void initSharedPreferences() {



        Bundle bundle = getIntent().getExtras();

        mToken = bundle.getString(Constants.TOKEN);
        mEmail = bundle.getString(Constants.EMAIL);
        listname = bundle.getString(Constants.LIST_NAME);

    }

    private void sorting(String division) {

        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).sort(mEmail,division)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));


    }

    private void handleResponse(ReferenceList list ) {

        lname.setText(list.getName());
        lddescription.setText(list.getDescription());
        lexample.setText(list.getSample());




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

    private void showMessage(String message) {

        mTvMessage.setVisibility(View.VISIBLE);
        mTvMessage.setText(message);

    }
    private void showSnackBarMessage(String message) {

        Snackbar.make(findViewById(R.id.listContent),message,Snackbar.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }



}
