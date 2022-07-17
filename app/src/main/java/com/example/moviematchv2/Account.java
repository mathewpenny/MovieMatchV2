package com.example.moviematchv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class Account extends AppCompatActivity {

    private ImageButton updateBtn, deleteBtn, reviewBtn, logoutBtn;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        logoutBtn = (ImageButton) findViewById(R.id.logoutButton);
        updateBtn = (ImageButton) findViewById(R.id.updateButton);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mAuth.signOut();
                Intent intent = new Intent(Account.this, Login.class);
                startActivity(intent);
                finish();
            }
        });


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mAuth.signOut();
                Intent intent = new Intent(Account.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed () {
        Intent intent = new Intent(Account.this, WelcomePage.class);
        startActivity(intent);
        finish();
    }
}

// Code saved for logout logic
  /*      // Firebase Sign Out
                mAuth.signOut();
                // Google Sign out
                gsc.signOut();
                 Facebook Sign Out
                LoginManager.getInstance().logOut();*/