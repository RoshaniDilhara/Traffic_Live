package com.example.trafficlive;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class

LoginActivity extends AppCompatActivity {


    private EditText userMail,userPassword;
    private Button btnLogin;
    private ProgressBar loginProgress;
    private FirebaseAuth mAuth;
    private Intent ProfileActivity;
    private ImageView loginPhoto;
    private TextView textViewSignUp;


    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        userMail = findViewById(R.id.login_mail);
        userPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.loginBtn);
        loginProgress = findViewById(R.id.login_progress);
        mAuth = FirebaseAuth.getInstance();
        textViewSignUp = findViewById(R.id.textViewSignUp);
       // ProfileActivity =  new Intent(this, com.example.trafficlive.ProfileActivity.class);
        loginPhoto = findViewById(R.id.login_photo);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        });

        loginProgress.setVisibility(View.INVISIBLE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginProgress.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.INVISIBLE);

                final String mail = userMail.getText().toString().trim();
                final String password = userPassword.getText().toString().trim();

                if(mail.isEmpty() || password.isEmpty()){
                    showMessage ("please verify All Field");
                    btnLogin.setVisibility(View.VISIBLE);
                    loginProgress.setVisibility(View.VISIBLE);
                }
                else
                {
                    //signIn(mail,password);
                    //progressDialog.setMessage("Registering Please Wait...");
                    //progressDialog.show();
                    signIn(mail,password);

                    //intent to profile page
                   // Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                    //startActivity(intent);
                    //finish();
                }
            }
        });
    }


    private void signIn(String mail, String password) {

        mAuth.signInWithEmailAndPassword(mail,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //progressDialog.dismiss();

                if (task.isSuccessful()) {

                    loginProgress.setVisibility(View.VISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                    updateUI();//start profile activity


                } else {
                    showMessage(task.getException().getMessage());
                    btnLogin.setVisibility(View.VISIBLE);
                    loginProgress.setVisibility(View.VISIBLE);
                }


            }
        });
    }

    private void updateUI() {
        Intent intent5 = new Intent(this, ProfileActivity.class);
        startActivity(intent5);
        finish();
        //startActivity(new Intent (getApplicationContext(),ProfileActivity.class));
        //finish();

    }

    private void showMessage(String text) {

        Toast.makeText(getApplicationContext(),text, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            //user is already connected so we need to redirect him to profile page
            updateUI();
        }

    }
}
