package com.example.moviematchv2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {

    ImageButton resetPasswordBtn;
    EditText passwordET, passwordConfirmET;

    private DatabaseReference userDb;
    private String userId, password;


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
            passwordET = findViewById(R.id.passwordReset);
            passwordConfirmET = findViewById(R.id.passwordConfirmReset);
            resetPasswordBtn = findViewById(R.id.resetPasswordBtn);

            resetPasswordBtn.setOnClickListener(view -> {
                if (user != null) {
                    if (!passwordET.equals("") || !passwordConfirmET.equals("")) {
                        Toast.makeText(ForgotPassword.this, "Password cannot be blank...", Toast.LENGTH_SHORT).show();
                    } if(passwordET.length() < 7) {
                        Toast.makeText(ForgotPassword.this, "Password must be more than 7 characters...", Toast.LENGTH_SHORT).show();
                    } if(!passwordConfirmET.equals(passwordET)) {
                        Toast.makeText(ForgotPassword.this, "Passwords must match...", Toast.LENGTH_SHORT).show();
                    } else {
                        password = passwordET.getText().toString();
                    }
                }
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("password", password);
                userDb.updateChildren(userInfo);
                Toast.makeText(ForgotPassword.this, "Information updated successfully.", Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(ForgotPassword.this, Login.class);
        startActivity(intent);
        finish();
    }
}