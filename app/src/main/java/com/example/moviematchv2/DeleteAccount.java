package com.example.moviematchv2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeleteAccount extends AppCompatActivity{

    ImageButton deleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        deleteAccount = findViewById(R.id.deleteButton);

        deleteAccount.setOnClickListener(view -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            assert user != null;
            user.delete().addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    Toast.makeText(DeleteAccount.this, "Your account is deleted. Goodbye!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DeleteAccount.this, LandingPage.class);
                    startActivity(intent);
                    finish();
                }
            });
        });
    }
}