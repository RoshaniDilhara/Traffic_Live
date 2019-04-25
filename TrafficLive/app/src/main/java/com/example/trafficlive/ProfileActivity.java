package com.example.trafficlive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.trafficlive.LoginActivity;
import com.example.trafficlive.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private Button savebtn;

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

        FirebaseUser user = mAuth.getCurrentUser();

       /* if(mAuth.getCurrentUser() != null){
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }*/


        savebtn =(Button) findViewById(R.id.savebtn);

        savebtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){

        if(view == savebtn){
            mAuth.signOut();
            finish();
            startActivity(new Intent(this, com.example.trafficlive.DataEntryFragment.class));
        }

    }



}
