package com.example.moviematchv2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;


import com.google.android.gms.common.api.ApiException;

import com.google.android.gms.tasks.Task;


public class LandingPage extends AppCompatActivity {


    private ImageButton loginBtn, registerBtn;
    private ImageButton googleSignIn, metaSignIn;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        // Button and TextView Variables
        loginBtn = (ImageButton) findViewById(R.id.loginButton);
        registerBtn = (ImageButton) findViewById(R.id.registerButton);
        googleSignIn = (ImageButton) findViewById(R.id.googleSignIn);
        metaSignIn = (ImageButton) findViewById(R.id.metaSignIn);

        //Hides action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();

            // Google Sign In variables
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            gsc = GoogleSignIn.getClient(this, gso);
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

            // allows user to not have to login each time they open the app
            if(account != null) {
                navigateToSecondActivity();
            }


            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LandingPage.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            });


            registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LandingPage.this, Register.class);
                    startActivity(intent);
                    finish();
                }
            });


            googleSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signIn();
                }
            });

            metaSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LandingPage.this, Register.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void navigateToSecondActivity() {
        finish();
        Intent intent = new Intent(LandingPage.this, WelcomePage.class);
        startActivity(intent);
    }

}