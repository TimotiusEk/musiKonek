package com.example.timotiusek.musikonek;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view__main_act)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout__main_act)
    DrawerLayout drawer;
    @BindView(R.id.toolbar__app_bar_main)
    Toolbar toolbar;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    ActionBarDrawerToggle toggle;
    BrowseFragment browseFragment;

    private String email;
    private String username;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
       showTheFirstFragment();

        sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);

        updateNavView();

        browseFragment = new BrowseFragment();

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView nv = (NavigationView) findViewById(R.id.nav_view);
        View header = nv.getHeaderView(0);

        TextView textName = (TextView) header.findViewById(R.id.username);
        textName.setText(username);

        TextView textEmail = (TextView) header.findViewById(R.id.email_label);
        textEmail.setText(email);

        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        setChecked(R.id.menu_home);
    }

    void showTheFirstFragment(){
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container__app_bar_main, new HomeFragment());
        mFragmentTransaction.commit();
    }

    public void changeFragment(Fragment newFragment) {
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container__app_bar_main, newFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_home) {
            changeFragment(new HomeFragment());
        } else if (id == R.id.menu_attendance) {
            changeFragment(new AttendanceFragment());
        } else if (id == R.id.menu_course) {
            changeFragment(new BrowseFragment());
        } else if (id == R.id.menu_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_exit_application) {
            System.exit(0);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setChecked(int id) {
        navigationView.setCheckedItem(id);
    }

    public void setHomeToBackBtn(final Fragment fragment) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(fragment);
                setSupportActionBar(toolbar);
                toggle = new ActionBarDrawerToggle(
                        MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.setDrawerListener(toggle);
                toggle.syncState();
            }
        });
    }

    public void updateNavView(){
        if(!sharedPreferences.getString("email","").equals("")) {
            email = sharedPreferences.getString("email","");
        }

        if(!sharedPreferences.getString("username","").equals("")) {
            username = sharedPreferences.getString("username","");
        }else{
            username = "username";
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ASDF","called");
        updateNavView();
    }
}

