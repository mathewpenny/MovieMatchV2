package com.example.moviematchv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class LobbyGuest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_guest);

    }
        @Override
        public void onBackPressed () {
            Intent intent = new Intent(LobbyGuest.this, WelcomePage.class);
            startActivity(intent);
            finish();
        }
    }