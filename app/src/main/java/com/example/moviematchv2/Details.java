package com.example.moviematchv2;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class Details extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        recyclerView = findViewById(R.id.recyclerView2);

        Intent intent = getIntent();
        int pos = intent.getIntExtra("position",0);
        Log.e("detailsPosition", String.valueOf(pos));
        PutDataIntoRecyclerView(Collections.singletonList(Swipe.moviesList.get(pos)));

    }

    private void PutDataIntoRecyclerView(List<Movie> moviesList) {
        adapter = new DetailsAdapter(this, moviesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}