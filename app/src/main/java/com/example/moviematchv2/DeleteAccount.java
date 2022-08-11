package com.example.moviematchv2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeleteAccount extends AppCompatActivity{

    ImageButton deleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        deleteAccount = findViewById(R.id.deleteButton);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Notification", " Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        deleteAccount.setOnClickListener(view -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
            user.delete().addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    Toast.makeText(DeleteAccount.this, "Your account is deleted. Goodbye!", Toast.LENGTH_SHORT).show();

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(DeleteAccount.this, "Notification");
                    builder.setContentTitle("Goodbye!");
                    builder.setContentText("We hope that you find your Movie Match out there!");
                    builder.setSmallIcon(R.drawable.logo);
                    builder.setAutoCancel(true);
                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(DeleteAccount.this);
                    managerCompat.notify(1, builder.build());

                    Intent intent = new Intent(DeleteAccount.this, LandingPage.class);
                    startActivity(intent);
                    finish();
                }
            });
        });
    }
    @Override
    public void onBackPressed () {
        Intent intent = new Intent(DeleteAccount.this, LobbyAccount.class);
        startActivity(intent);
        finish();
    }
}