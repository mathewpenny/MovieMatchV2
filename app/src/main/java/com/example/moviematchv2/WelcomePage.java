package com.example.moviematchv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomePage extends AppCompatActivity {

    Button host;
    Button join;
    Button account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        host = (Button) findViewById(R.id.HostButton);
        join = (Button) findViewById(R.id.JoinButton);
        account = (Button) findViewById(R.id.AccountButton);

        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(), LobbyHost.class);
                startActivity(intent);
                finish();
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(), LobbyGuest.class);
                startActivity(intent);
                finish();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(), LobbyAccount.class);
                startActivity(intent);
                finish();
            }
        });
    }
}