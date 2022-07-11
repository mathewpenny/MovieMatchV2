package com.example.moviematchv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LobbyHost extends AppCompatActivity {

    Button createLobby;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_host);

        createLobby = (Button) findViewById(R.id.createLobby);

        createLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetupFragment setupFragment = new SetupFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.setupFrag, setupFragment).commit();

            }
        });


    }
}