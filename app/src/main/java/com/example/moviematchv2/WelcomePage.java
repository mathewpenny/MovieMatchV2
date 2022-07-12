package com.example.moviematchv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class WelcomePage extends AppCompatActivity {

    ImageButton hostBtn, joinBtn, accountBtn;
    TextView name;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        hostBtn = (ImageButton) findViewById(R.id.hostButton);
        joinBtn = (ImageButton) findViewById(R.id.joinButton);
        accountBtn = (ImageButton) findViewById(R.id.accountButton);
        name = (TextView) findViewById(R.id.nameText);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) {
            String userName = account.getDisplayName();
            name.setText(userName);
        }

        hostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(), LobbyHost.class);
                startActivity(intent);
                finish();
            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(), LobbyGuest.class);
                startActivity(intent);
                finish();
            }
        });

        accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(), LobbyAccount.class);
                startActivity(intent);
                finish();
            }
        });
    }
}