package com.example.moviematchv2;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class Details extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DetailsAdapter adapter;
    String chosenStreaming, playLink;
    ImageButton playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        recyclerView = findViewById(R.id.recyclerView2);
        playButton = findViewById(R.id.playButton);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);
        chosenStreaming = intent.getStringExtra("chosenStreaming");
        playLink = intent.getStringExtra("link");

        PutDataIntoRecyclerView(Collections.singletonList(Swipe.moviesList.get(position)));

        // If User is signed into their streaming accounts, button will open that app. If it is not downloaded or signed in, Exception passes
        // Intent to the website instead. Need to drill into the response tree and get the link. Set up for link is
        // "streamingInfo" { "ca" { "link" : "https://blah blah "
        playButton.setOnClickListener(view -> {
            switch (chosenStreaming) {
                case "netflix":
                    try {
                        Intent launchIntent = new Intent(Intent.ACTION_VIEW);
                        launchIntent.setClassName("com.netflix.mediaclient", "com.netflix.mediaclient.ui.launch.UIWebViewActivity");
                        launchIntent.setData(Uri.parse(playLink));
                        startActivity(launchIntent);
                    } catch (Exception e) {
                        Intent launchIntent = new Intent(Intent.ACTION_VIEW);
                        launchIntent.setData(Uri.parse(playLink));
                        startActivity(launchIntent);
                    }
                    break;
                case "prime":
                    try {
                        Intent launchIntent = new Intent(Intent.ACTION_VIEW);
                        launchIntent.setClassName("com.amazon.firebat", "com.amazon.firebat.deeplink.DeepLinkRoutingActivity");
                        launchIntent.setData(Uri.parse(playLink));
                        startActivity(launchIntent);
                    } catch (Exception e) {
                        Intent launchIntent = new Intent(Intent.ACTION_VIEW);
                        launchIntent.setData(Uri.parse(playLink));
                        startActivity(launchIntent);
                    }
                    break;
                case "disney":
                    try {
                        Intent launchIntent = new Intent(Intent.ACTION_VIEW);
                        launchIntent.setClassName("com.disney.disneyplus", "com.bamtechmedia.dominguez.main.MainActivity");
                        launchIntent.setData(Uri.parse(playLink));
                        startActivity(launchIntent);
                    } catch (Exception e) {
                        Intent launchIntent = new Intent(Intent.ACTION_VIEW);
                        launchIntent.setData(Uri.parse(playLink));
                        startActivity(launchIntent);
                    }
                    break;
            }
        });

    }

    private void PutDataIntoRecyclerView(List<Movie> moviesList) {
        adapter = new DetailsAdapter(this, moviesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}