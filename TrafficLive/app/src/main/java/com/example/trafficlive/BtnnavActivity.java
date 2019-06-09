package com.example.trafficlive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class BtnnavActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //savedInstanceState = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btnnav);

        BottomNavigationView bottomNav=findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListner);

        Bundle bundle = getIntent().getExtras();


            DataEntryFragment fragobj = new DataEntryFragment();
           fragobj.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragobj).commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        navigationView.setNavigationItemSelectedListener(this);
    }
/*
    public void onResume(){
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        DataEntryFragment fragobj = new DataEntryFragment();
        fragobj.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragobj).commit();


    }

*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment=null;

            switch (menuItem.getItemId()){
                case R.id.dataEntrty_bottom:
                   selectedFragment=new DataEntryFragment();
                     break;
                case R.id.violations_bottom:
                    selectedFragment=new ViolationFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

            return true;
        }
    };



    Fragment selectedFragment1=null;

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            selectedFragment1=new DataEntryFragment();
        } else if (id == R.id.nav_contact_us){
            selectedFragment1=new ContactUsFragment();
        } else if (id == R.id.nav_help) {
            selectedFragment1=new HelpFragment();
        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(BtnnavActivity.this,WelcomeActivity.class));
            finish();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment1).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
