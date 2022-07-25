package com.example.moviematchv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class LobbyHost extends AppCompatActivity {
    private Spinner streamingSpn;
    private Spinner genreSpn;
    private Spinner typeSpn;
    private EditText phoneNumber;
    private ImageButton sendBtn;
    private String friendNumber;
    private Integer chosenGenre;
    private String chosenStreaming;
    private String chosenType;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_host);

            streamingSpn = (Spinner) findViewById(R.id.streamingSpinner);
            genreSpn = (Spinner) findViewById(R.id.genreSpinner);
            phoneNumber = (EditText) findViewById(R.id.phoneNumber);
            sendBtn = (ImageButton) findViewById(R.id.sendRequestButton);

            drawerLayout = findViewById(R.id.drawer_view);
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            friendNumber = phoneNumber.getEditableText().toString();

            streamingSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    chosenStreaming = streamingSpn.getSelectedItem().toString();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    Toast.makeText(LobbyHost.this, "Please choose a service.", Toast.LENGTH_SHORT).show();
                    streamingSpn.requestFocus();
                }
            });

            typeSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    chosenType = typeSpn.getSelectedItem().toString();
                }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(LobbyHost.this, "Please choose a type.", Toast.LENGTH_SHORT).show();
                streamingSpn.requestFocus();
                }
            });

            genreSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (genreSpn.getSelectedItem().toString()) {
                        case "Action":
                            chosenGenre = 28;
                            break;
                        case "Adventure":
                            chosenGenre = 12;
                            break;
                        case "Adult Animation":
                            chosenGenre = 7;
                            break;
                        case "Animation":
                            chosenGenre = 16;
                            break;
                        case "Biography":
                            chosenGenre = 1;
                            break;
                        case "Comedy":
                            chosenGenre = 35;
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
                        case "Horror":
                            chosenGenre = 27;
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
                        chosenGenre = 0; //can change the default to set no genre
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            //Must both send an SMS and forward data to swipe for API call
            sendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (streamingSpn.getSelectedItem().toString().equals("Choose Platform")) {
                        Toast.makeText(LobbyHost.this, "Please choose a service", Toast.LENGTH_SHORT).show();
                        streamingSpn.requestFocus();
                    }if(genreSpn.getSelectedItem().toString().equals("Choose Genre")){
                        Toast.makeText(LobbyHost.this, "Please choose a Genre", Toast.LENGTH_SHORT).show();
                        genreSpn.requestFocus();
                    }if(typeSpn.getSelectedItem().toString().equals("Choose Type")){
                        Toast.makeText(LobbyHost.this, "Please choose a Type", Toast.LENGTH_SHORT).show();
                        genreSpn.requestFocus();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), Swipe.class);
                        intent.putExtra("streaming", chosenStreaming);
                        intent.putExtra("genre", chosenGenre);
                        startActivity(intent);
                        finish();
                    }
                }
            });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(LobbyHost.this, WelcomePage.class);
        startActivity(intent);
        finish();
    }
}