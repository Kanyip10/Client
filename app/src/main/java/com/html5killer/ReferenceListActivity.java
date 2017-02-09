package com.html5killer;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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

import static com.html5killer.R.id.list;


/**
 * Created by Ashley Wong on 30/1/2017.
 */

public class ReferenceListActivity extends AppCompatActivity {
    public static final String TAG = ReferenceListActivity.class.getSimpleName();

    private ListView listView ;
    private Spinner spinner;
    private TextView mTvMessage;


   private CompositeSubscription mSubscriptions;
    private String mToken;
    private String mEmail;

    private String[] sorted = {"a","b","c", "d"};



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.referencelist);
        mSubscriptions = new CompositeSubscription();
        initViews();
        initSharedPreferences();



        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                switch (position){
                case 0:
                sorting(spinner.getSelectedItem().toString());
                    break;
                case 1:
                sorting(spinner.getSelectedItem().toString());
                        break;
                case 2:
                sorting(spinner.getSelectedItem().toString());
                        break;

                }


            }
            public void onNothingSelected(AdapterView arg0) {

            }
        });
    }



    private void initSharedPreferences() {



            Bundle bundle = getIntent().getExtras();

            mToken = bundle.getString(Constants.TOKEN);
            mEmail = bundle.getString(Constants.EMAIL);

    }


    private void initViews() {
        spinner = (Spinner) findViewById(R.id.list_spinner);
        spinner.setAdapter(spinnerView());
        spinner.setSelection(0);
        listView = (ListView) findViewById(list);

    }

    private ArrayAdapter<CharSequence> spinnerView(){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this,
                R.array.list_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    return adapter;
    }



    private ArrayAdapter<String> selectedList(String[] strings){
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, strings);
        return adapter;
    }


    private void sorting(String division) {

        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).sort(mEmail,division)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));


    }

    private void handleResponse(ReferenceList list) {

        for(int i = 0; i < sorted.length; i++){
            sorted[0] = list.getName();
        }


        listView.setAdapter(selectedList(sorted));


    }

    private void handleError(Throwable error) {



        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            //showMessage("Network Error !");
        }
    }

    private void showMessage(String message) {

        mTvMessage.setVisibility(View.VISIBLE);
        mTvMessage.setText(message);

    }
    private void showSnackBarMessage(String message) {

        Snackbar.make(findViewById(R.id.activity_profile),message,Snackbar.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }


}
