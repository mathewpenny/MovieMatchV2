package com.example.moviematchv2;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class Details extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DetailsAdapter adapter;
    private String chosenStreaming, playLink;
    private ImageButton playButton;

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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Notification", " Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        // If User is signed into their streaming accounts, button will open that app. If it is not downloaded or signed in, Exception passes
        // Intent to the website instead.
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
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(Details.this, "Notification");
                    builder.setContentTitle("Let's Start Watching!");
                    builder.setContentText("Enjoy your film/show! Don't forget to tell your friends!");
                    builder.setSmallIcon(R.drawable.logo);
                    builder.setAutoCancel(true);
                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Details.this);
                    managerCompat.notify(1, builder.build());
        });

    }

    private void PutDataIntoRecyclerView(List<Movie> moviesList) {
        adapter = new DetailsAdapter(this, moviesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}