package com.example.moviematchv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private EditText nameET, emailET, phoneET, passwordET, confirmPasswordET;
    private ImageButton registerBtn;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // StateListener will be listening for the state of login of the user
        mAuth = FirebaseAuth.getInstance();

        // for returning users (aka most people using the app on their phone),
        // this will check and if logged in from before, will go direct to
        //landing page, else will stay on login until user is logged in.
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) { // means user is logged in so we can move on in the app
                    Intent intent = new Intent(Register.this, WelcomePage.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        nameET = (EditText) findViewById(R.id.registerName);
        emailET = (EditText) findViewById(R.id.registerEmail);
        phoneET = (EditText) findViewById(R.id.registerPhone);
        passwordET = (EditText) findViewById(R.id.registerPassword);
        confirmPasswordET = (EditText) findViewById(R.id.confirmPassword);

        registerBtn = (ImageButton) findViewById(R.id.registerButton);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String name = nameET.getText().toString();
                final String email = emailET.getText().toString();
                final String password = passwordET.getText().toString();
                final String confirmPass = confirmPasswordET.getText().toString();

                if (passwordET.getText().toString().equals("")){
                    Toast.makeText(Register.this, "Enter a password...", Toast.LENGTH_LONG).show();
                    passwordET.requestFocus();
                } if (passwordET.getText().toString().length() <= 7) {
                    Toast.makeText(Register.this, "Enter a password...", Toast.LENGTH_LONG).show();
                    passwordET.requestFocus();
                } if (!confirmPasswordET.getText().toString().equals(passwordET.getText().toString())) {
                    Toast.makeText(Register.this, "Passwords must match", Toast.LENGTH_LONG).show();
                    confirmPasswordET.requestFocus();
                } if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailET.getText().toString()).matches()) {
                    Toast.makeText(Register.this, "Enter a valid email address...", Toast.LENGTH_LONG).show();
                    emailET.requestFocus();
                } if(emailET.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "Enter an email address...", Toast.LENGTH_LONG).show();
                    emailET.requestFocus();
                }

                if(password.length() > 7 && password.equals(confirmPass)) {
                    // Create the user here with onCompleteListener to check if task is successful
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //this will only be triggered if the registration was not successful.
                            if (!task.isSuccessful()) {
                                Toast.makeText(Register.this, "Registration Error", Toast.LENGTH_SHORT).show();
                            } else if(task.isSuccessful()){
                                Toast.makeText(Register.this, "Registration Complete!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Register.this, WelcomePage.class);
                                startActivity(intent);
                                finish();
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
    @Override
    public void onBackPressed () {
        Intent intent = new Intent(Register.this, LandingPage.class);
        startActivity(intent);
        finish();
    }
}