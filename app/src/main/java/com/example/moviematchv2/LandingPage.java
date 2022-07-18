package com.example.moviematchv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;


import com.google.android.gms.common.api.ApiException;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;


public class LandingPage extends AppCompatActivity {


    private ImageButton loginBtn, registerBtn;
    private ImageButton googleSignIn, metaSignIn;

    private ImageView image;
    private TextView logoText;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        loginBtn = (ImageButton) findViewById(R.id.loginButton);
        registerBtn = (ImageButton) findViewById(R.id.registerButton);
        googleSignIn = (ImageButton) findViewById(R.id.googleSignIn);
        metaSignIn = (ImageButton) findViewById(R.id.metaSignIn);

        image = findViewById(R.id.imageView);
        logoText = findViewById(R.id.textView);

        mAuth = FirebaseAuth.getInstance();


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();


            metaSignIn.setOnClickListener(view -> {
                Intent intent = new Intent(LandingPage.this, FacebookSignIn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            });

            // Google Login
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail().build();
            gsc = GoogleSignIn.getClient(this, gso);
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

            // allows user to not have to login each time they open the app
            if(account != null) {
                navigateToSecondActivity();
            }

            googleSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signIn();
                }
            });


            // Navigation Logic here
            // Navigate to Login page for regular Email/Password Firebase
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LandingPage.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            });
            // Navigate to Register Page for regular Email/Password Firebase
            registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LandingPage.this, Register.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    // End of onCreate method here
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
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }






    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnSuccessListener(this, authResult -> {
            navigateToSecondActivity();
            finish();
        });
    }
    private void navigateToSecondActivity() {
        finish();
        Intent intent = new Intent(LandingPage.this, WelcomePage.class);
        startActivity(intent);
    }
}