package com.html5killer;


import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
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




/**
 * Created by Ashley Wong on 30/1/2017.
 */

public class ReferenceListActivity extends AppCompatActivity {
    public static final String TAG = ReferenceListActivity.class.getSimpleName();



    private ListView listView ;
    private ListView listView1 ;
    private ListView listView2 ;
    private ListView listView3 ;
    private ListView listView4 ;
    private TextView header;
    private TextView header1;
    private TextView header2;
    private TextView header3;
    private TextView header4;
    private Spinner spinner;
    private TextView mTvMessage;


   private CompositeSubscription mSubscriptions;
    private String mToken;
    private String mEmail;

    private String[] list1 = {"<!--...-->","<!DOCTYPE>","<a>","<abbr>","<acronym>","<address>","<applet>",
            "<area>","<article>","<aside>","<audio>","<b>","<base>","<basefont>","<bdi>","<bdo>","<big>",
        "<blockquote>","<body>","<br>","<button>","<canvas>","<caption>","<center>","<cite>","<code>","<col>",
        "<colgroup>","<command>","<datalist>","<dd>","<del>","<details>","<dfn>","<div>","<dl>","<dt>","<em>",
            "<embed>","<fieldset>","<figcaption>","<figure>","<font>","<footer>","<form>","<frame>","<frameset>",
        "<h1> - <h6>","<head>","<header>","<hgroup>","<hr>","<html>","<i>","<iframe>","<img>","<input>","<ins>",
           "<kbd>","<keygen>","<label>","<legend>","<li>","<link>","<main>","<map>","<mark>","<menu>","<table>"};


    private static final String[] list2 = {"<!--...-->","<!DOCTYPE>","<body>","<br>","<h1> - <h6>","<head>","<header>","<hr>"
            ,"<html>","<p>"};

    private static final String[] list3 = {"<table>","<caption>","<th>","<tr>","<td>","<thead>"
    ,"<tbody>","<tfoot>","<col>","<colgroup>"};

    private static final String[] list4 = {"<ul>","<ol>","<li>","<dl>","<dt>","<dd>"};

    private static final String[] list5 = {"<html>","<body>","<p>","<h1> - <h6>","<br>"};






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.referencelist);
        mSubscriptions = new CompositeSubscription();
        initViews();
        initSharedPreferences();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                switch (position){
                case 0:
                //sorting(spinner.getSelectedItem().toString());
                    header.setText("");
                    header1.setText("");
                    header2.setText("");
                    header3.setText("");
                    header4.setText("");



                    listView.setAdapter(selectedList(list1));
                    ListUtils.setDynamicHeight(listView);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                                        if(position == 0) {
                                            Intent intent = new Intent(ReferenceListActivity.this, listContent.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString(Constants.EMAIL, mEmail);
                                            bundle.putString(Constants.TOKEN, mToken);
                                            bundle.putString(Constants.LIST_NAME, (String) listView.getItemAtPosition(position));
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        }

                                         else{
                                            Intent intent = new Intent(ReferenceListActivity.this, listContent.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString(Constants.EMAIL, mEmail);
                                            bundle.putString(Constants.TOKEN, mToken);
                                            bundle.putString(Constants.LIST_NAME, (String) listView.getItemAtPosition(position));
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        }




                        }
                    });
                    break;
                case 1:
               // sorting(spinner.getSelectedItem().toString());
                    header.setText("basic");
                    listView.setAdapter(selectedList(list2));
                    header1.setText("table");
                    listView1.setAdapter(selectedList(list3));
                    header2.setText("list");
                    listView2.setAdapter(selectedList(list4));

                    ListUtils.setDynamicHeight(listView);
                    ListUtils.setDynamicHeight(listView1);
                    ListUtils.setDynamicHeight(listView2);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                            if(position == 0) {
                                Intent intent = new Intent(ReferenceListActivity.this, listContent.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.EMAIL, mEmail);
                                bundle.putString(Constants.TOKEN, mToken);
                                bundle.putString(Constants.LIST_NAME, (String) listView.getItemAtPosition(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }

                            else{
                                Intent intent = new Intent(ReferenceListActivity.this, listContent.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.EMAIL, mEmail);
                                bundle.putString(Constants.TOKEN, mToken);
                                bundle.putString(Constants.LIST_NAME, (String) listView.getItemAtPosition(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }




                        }
                    });
                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                            if(position == 0) {
                                Intent intent = new Intent(ReferenceListActivity.this, listContent.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.EMAIL, mEmail);
                                bundle.putString(Constants.TOKEN, mToken);
                                bundle.putString(Constants.LIST_NAME, (String) listView.getItemAtPosition(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }

                            else{
                                Intent intent = new Intent(ReferenceListActivity.this, listContent.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.EMAIL, mEmail);
                                bundle.putString(Constants.TOKEN, mToken);
                                bundle.putString(Constants.LIST_NAME, (String) listView.getItemAtPosition(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }




                        }
                    });
                    listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                            if(position == 0) {
                                Intent intent = new Intent(ReferenceListActivity.this, listContent.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.EMAIL, mEmail);
                                bundle.putString(Constants.TOKEN, mToken);
                                bundle.putString(Constants.LIST_NAME, (String) listView.getItemAtPosition(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }

                            else{
                                Intent intent = new Intent(ReferenceListActivity.this, listContent.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.EMAIL, mEmail);
                                bundle.putString(Constants.TOKEN, mToken);
                                bundle.putString(Constants.LIST_NAME, (String) listView.getItemAtPosition(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }




                        }
                    });


                    break;
                case 2:
               // sorting(spinner.getSelectedItem().toString());
                    header.setText("chapter1");
                    listView.setAdapter(selectedList(list5));
                    header1.setText("chapter2");
                    listView1.setAdapter(selectedList(list3));
                    header2.setText("chapter3");
                    listView2.setAdapter(selectedList(list4));

                    ListUtils.setDynamicHeight(listView);
                    ListUtils.setDynamicHeight(listView1);
                    ListUtils.setDynamicHeight(listView2);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                            if(position == 0) {
                                Intent intent = new Intent(ReferenceListActivity.this, listContent.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.EMAIL, mEmail);
                                bundle.putString(Constants.TOKEN, mToken);
                                bundle.putString(Constants.LIST_NAME, (String) listView.getItemAtPosition(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }

                            else{
                                Intent intent = new Intent(ReferenceListActivity.this, listContent.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.EMAIL, mEmail);
                                bundle.putString(Constants.TOKEN, mToken);
                                bundle.putString(Constants.LIST_NAME, (String) listView.getItemAtPosition(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }




                        }
                    });

                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                            if(position == 0) {
                                Intent intent = new Intent(ReferenceListActivity.this, listContent.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.EMAIL, mEmail);
                                bundle.putString(Constants.TOKEN, mToken);
                                bundle.putString(Constants.LIST_NAME, (String) listView.getItemAtPosition(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }

                            else{
                                Intent intent = new Intent(ReferenceListActivity.this, listContent.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.EMAIL, mEmail);
                                bundle.putString(Constants.TOKEN, mToken);
                                bundle.putString(Constants.LIST_NAME, (String) listView.getItemAtPosition(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }




                        }
                    });
                    listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                            if(position == 0) {
                                Intent intent = new Intent(ReferenceListActivity.this, listContent.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.EMAIL, mEmail);
                                bundle.putString(Constants.TOKEN, mToken);
                                bundle.putString(Constants.LIST_NAME, (String) listView.getItemAtPosition(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }

                            else{
                                Intent intent = new Intent(ReferenceListActivity.this, listContent.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.EMAIL, mEmail);
                                bundle.putString(Constants.TOKEN, mToken);
                                bundle.putString(Constants.LIST_NAME, (String) listView.getItemAtPosition(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }




                        }
                    });


                    break;


                }


            }
            public void onNothingSelected(AdapterView arg0) {

            }
        });
    }

    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
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
        listView = (ListView) findViewById(R.id.list1);
        listView1 = (ListView) findViewById(R.id.list2);
        listView2 = (ListView) findViewById(R.id.list3);
        listView3 = (ListView) findViewById(R.id.list4);
        listView4 = (ListView) findViewById(R.id.list5);
        header = (TextView) findViewById(R.id.separator1);
        header1 = (TextView) findViewById(R.id.separator2);
        header2 = (TextView) findViewById(R.id.separator3);
        header3 = (TextView) findViewById(R.id.separator4);
        header4 = (TextView) findViewById(R.id.separator5);


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


  /*  private void sorting(String division) {

        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).sort(mEmail,division)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));


    }

    private void handleResponse(ReferenceList list ) {


      //  for(int i = 0; i < sorted.length; i++){
        // sorted[0] = list.getName();
        //}




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

        Snackbar.make(findViewById(R.id.referencelist),message,Snackbar.LENGTH_SHORT).show();

    }
*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        }
    }

