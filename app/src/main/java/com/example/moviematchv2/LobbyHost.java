package com.example.moviematchv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class LobbyHost extends AppCompatActivity {
    Spinner streaming;
    Spinner genre;
    EditText phoneNumber;
    ImageButton send;
    String friendNumber;
    int chosenGenre;
    String chosenStreaming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_host);

        streaming = (Spinner) findViewById(R.id.streamingSpinner);
        genre = (Spinner) findViewById(R.id.genreSpinner);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        send = (ImageButton) findViewById(R.id.sendRequestButton);

        if(genre.getSelectedItem().toString() != null){

            switch (genre.getSelectedItem().toString()){
                case "Adventure":
                    chosenGenre = 12 ;
                    break;
                case "Action":
                    chosenGenre = 28;
                    break;
                case "Animation":
                    chosenGenre = 16 ;
                    break;
                case "Biography":
                    chosenGenre = 1 ;
                    break;
                case "Comedy":
                    chosenGenre = 35 ;
                    break;
                case "Crime":
                    chosenGenre = 80;
                    break;
                case "Documentary":
                    chosenGenre = 99;
                    break;
                case "Drama":
                    chosenGenre = 18;
                    break;
                case "Family":
                    chosenGenre = 10751;
                    break;
                case "Fantasy":
                    chosenGenre = 14;
                    break;
                case "Musical":
                    chosenGenre = 4;
                    break;
                case "Reality":
                    chosenGenre = 10764;
                    break;
                case "Romance":
                    chosenGenre = 10749;
                    break;
                case "Science Fiction":
                    chosenGenre = 878;
                    break;
                case "Thriller":
                    chosenGenre = 53;
                    break;
                case "Western":
                    chosenGenre = 37;
                    break;
                default:
                    chosenGenre = 28; //can change the default to set no genre
            }
        }

        friendNumber = phoneNumber.getEditableText().toString();

        streaming.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chosenStreaming = streaming.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        genre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Log.isLoggable("test", chosenGenre);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Must both send an SMS and forward data to swipe for API call
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Swipe.class);
                intent.putExtra("streaming", chosenStreaming);
                intent.putExtra("genre", chosenGenre);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(LobbyHost.this, WelcomePage.class);
        startActivity(intent);
        finish();
    }
}