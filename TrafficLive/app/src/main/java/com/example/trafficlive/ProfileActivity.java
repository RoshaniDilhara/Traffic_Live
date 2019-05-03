package com.example.trafficlive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private Button savebtn;
    private Button getlogin;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        }
//
        FirebaseUser user = mAuth.getCurrentUser();
//
//       /* if(mAuth.getCurrentUser() != null){
//            //profile activity here
//            finish();
//            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
//        }*/

        getlogin =(Button) findViewById(R.id.getlogin);
        getlogin.setOnClickListener(this);

        savebtn =(Button) findViewById(R.id.savebtn);

        savebtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view == getlogin){
            mAuth.signOut();
            getlogin.setVisibility(View.GONE);
            savebtn.setVisibility(View.GONE);
            Intent intent6 = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent6);
            finish();

        }
        if(view == savebtn){
            mAuth.signOut() ;

            savebtn.setVisibility(View.GONE);
            getlogin.setVisibility(View.GONE);
//            FragmentManager fm = getSupportFragmentManager();
//            DataEntryFragment fragment =new DataEntryFragment();
//            fm.beginTransaction().replace(R.id.container,fragment).commit();

            Intent intent7 = new Intent(ProfileActivity.this, BtnnavActivity.class);
            startActivity(intent7);
            finish();


//            Intent intent5 = new Intent(this, DataEntryFragment.class);
//            startActivity(intent5);
//            finish();
        }
    }

//    @Override
//    public void onClick(View view){
//
//        if(view == savebtn){
//            mAuth.signOut() ;
//
//            savebtn.setVisibility(View.GONE);
//            getlogin.setVisibility(View.GONE);
//            FragmentManager fm = getSupportFragmentManager();
//            DataEntryFragment fragment =new DataEntryFragment();
//            fm.beginTransaction().replace(R.id.container,fragment).commit();
//
////            Intent intent5 = new Intent(this, DataEntryFragment.class);
////            startActivity(intent5);
////            finish();
//        }
//
//    }



}
