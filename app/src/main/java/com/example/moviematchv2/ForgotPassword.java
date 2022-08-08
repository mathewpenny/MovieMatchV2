package com.example.moviematchv2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ForgotPassword extends AppCompatActivity {

    ImageButton resetPasswordBtn;
    EditText password, passwordConfirm;

    private DatabaseReference userDb;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //Hides action bar
        if (getSupportActionBar() != null) {

            getSupportActionBar().hide();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();

            userId = mAuth.getCurrentUser().getUid();
            userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
            password = findViewById(R.id.passwordReset);
            passwordConfirm = findViewById(R.id.passwordConfirmReset);
            resetPasswordBtn = findViewById(R.id.resetPasswordBtn);

                if (user != null) {
                    String emailAddress = user.getEmail();
                    mAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.e("EMAIL", "Email sent.");
                        Intent intent = new Intent(ForgotPassword.this, Login.class);
                        startActivity(intent);
                    }
                });
            }
        }
    }
    @Override
    public void onBackPressed () {
        Intent intent = new Intent(ForgotPassword.this, Login.class);
        startActivity(intent);
        finish();
    }
}