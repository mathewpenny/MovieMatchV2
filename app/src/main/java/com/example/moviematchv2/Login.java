package com.example.moviematchv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText emailET, passwordET;
    private ImageButton loginBtn;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // StateListener will be listening for the state of login of the user
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) { // means user is logged in so we can move on in the app
                    Intent intent = new Intent(Login.this, AccountPage.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        emailET = (EditText) findViewById(R.id.loginEmail);
        passwordET = (EditText) findViewById(R.id.loginPassword);

        loginBtn = (ImageButton) findViewById(R.id.loginButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailET.getText().toString();
                final String password = passwordET.getText().toString();

                if(email.equals("")) {
                    Toast.makeText(Login.this, "Email cannot be blank", Toast.LENGTH_SHORT).show();
                    emailET.requestFocus();
                }

                if(password.equals("")) {
                    Toast.makeText(Login.this, "Password cannot be blank", Toast.LENGTH_SHORT).show();
                    passwordET.requestFocus();
                    }
                else {
                    // Create the user here with onCompleteListener to check if task is successful
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //this will only be triggered if the registration was not successful.
                            if (!task.isSuccessful()) {
                                Toast.makeText(Login.this, "Login Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
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
}