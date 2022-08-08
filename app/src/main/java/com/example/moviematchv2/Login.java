package com.example.moviematchv2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText emailET, passwordET;
    private ImageButton loginBtn, backBtn;
    private TextView forgotPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hides action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();

            // StateListener will be listening for the state of login of the user
            mAuth = FirebaseAuth.getInstance();
            firebaseAuthStateListener = firebaseAuth -> {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) { // means user is logged in so we can move on in the app
                    Intent intent = new Intent(Login.this, WelcomePage.class);
                    startActivity(intent);
                    finish();
                }
            };

            emailET = (EditText) findViewById(R.id.loginEmail);
            passwordET = (EditText) findViewById(R.id.loginPassword);

            loginBtn = (ImageButton) findViewById(R.id.loginButton);
            backBtn = (ImageButton) findViewById(R.id.backButton);

            forgotPassword = (TextView) findViewById(R.id.forgotPassLink);
            forgotPassword.setOnClickListener(view -> {
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);
                finish();
            });

            backBtn.setOnClickListener(view -> {
                    Intent intent = new Intent(getApplicationContext(), LandingPage.class);
                    startActivity(intent);
                });

            loginBtn.setOnClickListener(view -> {
                final String email = emailET.getText().toString();
                final String password = passwordET.getText().toString();

                if (email.equals("")) {
                    Toast.makeText(Login.this, "Email cannot be blank", Toast.LENGTH_SHORT).show();
                    emailET.requestFocus();
                }
                if (password.equals("")) {
                    Toast.makeText(Login.this, "Password cannot be blank", Toast.LENGTH_SHORT).show();
                    passwordET.requestFocus();
                } else {
                    // Create the user here with onCompleteListener to check if task is successful
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, task -> {
                        //this will only be triggered if the registration was not successful.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Login.this, "Login Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(Login.this, LandingPage.class);
        startActivity(intent);
        finish();
    }
}