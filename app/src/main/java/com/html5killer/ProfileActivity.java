package com.html5killer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.html5killer.fragments.ChangePasswordDialog;
import com.html5killer.fragments.ErrorGameFragment;
import com.html5killer.fragments.Profile_fragment;
import com.html5killer.fragments.TagGameFragment;
import com.html5killer.fragments.TutorialFragment;
import com.html5killer.model.Response;
import com.html5killer.model.User;
import com.html5killer.network.NetworkUtil;
import com.html5killer.utils.Constants;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;



public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,ChangePasswordDialog.Listener {

    public static final String TAG = ProfileActivity.class.getSimpleName();

    private SharedPreferences mSharedPreferences;
    private String mToken;
    private String mEmail;

    private CompositeSubscription mSubscriptions;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mSubscriptions = new CompositeSubscription();
//        initViews();
        initSharedPreferences();
      //  loadProfile();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_profile);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



    }


    private void initSharedPreferences() {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mToken = mSharedPreferences.getString(Constants.TOKEN, "");
        mEmail = mSharedPreferences.getString(Constants.EMAIL, "");
    }

    private void logout() {

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.EMAIL,"");
        editor.putString(Constants.TOKEN,"");
        editor.apply();
        finish();
    }

    private void showDialog(){

        ChangePasswordDialog fragment = new ChangePasswordDialog();

        Bundle bundle = new Bundle();
        bundle.putString(Constants.EMAIL, mEmail);
        bundle.putString(Constants.TOKEN,mToken);
        fragment.setArguments(bundle);

        fragment.show(getFragmentManager(), ChangePasswordDialog.TAG);
    }


    private void showSnackBarMessage(String message) {

        Snackbar.make(findViewById(R.id.content_main), message, Snackbar.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_profile);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    // @SuppressWarnings("StatementWithEmptyBody")
    // @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



         if (id == R.id.nav_gallery) {

            Intent intent = new Intent(this, ReferenceListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.EMAIL, mEmail);
            bundle.putString(Constants.TOKEN, mToken);
            intent.putExtras(bundle);
            startActivity(intent);


        } else if (id == R.id.nav_logout) {
            logout();
        }
        else if (id == R.id.nav_changePassword) {
        showDialog();
    }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_profile);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    TutorialFragment tag1 = new TutorialFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EMAIL, mEmail);
                    bundle.putString(Constants.TOKEN, mToken);
                    tag1.setArguments(bundle);
                    return tag1;

                case 1:
                    Profile_fragment tag3 = new Profile_fragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString(Constants.EMAIL, mEmail);
                    bundle2.putString(Constants.TOKEN, mToken);
                    tag3.setArguments(bundle2);
                    return tag3;

            }
            return null;
        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Tutorials";
                case 1:
                    return "Profile";

            }
            return null;
        }
    }
    @Override
    public void onPasswordChanged() {

        showSnackBarMessage("Password Changed Successfully !");
    }


}
