package com.example.moviematchv2;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText nameET, emailET, phoneET, passwordET, confirmPasswordET;
    private ImageButton registerBtn;
    private CheckBox privacyCheck;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Hides action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();

            mAuth = FirebaseAuth.getInstance();

            // StateListener will be listening for the state of login of the user
            // for returning users (aka most people using the app on their phone),
            // this will check and if logged in from before, will go direct to
            // landing page, else will stay on login until user is logged in.
            firebaseAuthStateListener = firebaseAuth -> {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) { // means user is logged in so we can move on in the app
                    Intent intent = new Intent(Register.this, WelcomePage.class);
                    startActivity(intent);
                    finish();
                }
            };

            privacyCheck = (CheckBox) findViewById(R.id.privacyCheck);
            privacyHyperLink();
            nameET = (EditText) findViewById(R.id.registerName);
            emailET = (EditText) findViewById(R.id.registerEmail);
            phoneET = (EditText) findViewById(R.id.registerPhone);
            passwordET = (EditText) findViewById(R.id.registerPassword);
            confirmPasswordET = (EditText) findViewById(R.id.confirmPassword);

            registerBtn = (ImageButton) findViewById(R.id.registerButton);

            registerBtn.setOnClickListener(view -> {
                final String name = nameET.getText().toString();
                final String email = emailET.getText().toString();
                final String phone = phoneET.getText().toString();
                final String password = passwordET.getText().toString();
                final String confirmPass = confirmPasswordET.getText().toString();

                if (passwordET.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "Enter a password...", Toast.LENGTH_LONG).show();
                    passwordET.requestFocus();
                }
                if (passwordET.getText().toString().length() <= 7) {
                    Toast.makeText(Register.this, "Password is not long enough...", Toast.LENGTH_LONG).show();
                    passwordET.requestFocus();
                }
                if (!confirmPasswordET.getText().toString().equals(passwordET.getText().toString())) {
                    Toast.makeText(Register.this, "Passwords must match", Toast.LENGTH_LONG).show();
                    confirmPasswordET.requestFocus();
                }
                if (!confirmPasswordET.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "Please confirm password...", Toast.LENGTH_LONG).show();
                    confirmPasswordET.requestFocus();
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailET.getText().toString()).matches()) {
                    Toast.makeText(Register.this, "Enter a valid email address...", Toast.LENGTH_LONG).show();
                    emailET.requestFocus();
                }
                if (emailET.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "Enter an email address...", Toast.LENGTH_LONG).show();
                    emailET.requestFocus();
                }

                if (password.length() > 7 && password.equals(confirmPass) && privacyCheck.isChecked()) {
                    // Create the user here with onCompleteListener to check if task is successful
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this, task -> {
                        //this will only be triggered if the registration was not successful.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Register.this, "Registration Error", Toast.LENGTH_SHORT).show();
                        } else if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Registration Complete!", Toast.LENGTH_SHORT).show();
                            String userId = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("email");
                            currentUserDb.setValue(email);
                            currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("name");
                            currentUserDb.setValue(name);
                            currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("phone");
                            currentUserDb.setValue(phone);

                            Intent intent = new Intent(Register.this, WelcomePage.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            });
        }
    }

    private void privacyHyperLink() {
        TextView privacyCheck = findViewById(R.id.privacyCheck);
        privacyCheck.setMovementMethod(LinkMovementMethod.getInstance());
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