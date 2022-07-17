package com.example.moviematchv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class FAQ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(FAQ.this, WelcomePage.class);
        startActivity(intent);
        finish();
    }
}