package com.example.moviematchv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Swipe extends AppCompatActivity {

    // Variables for API call and Swipeable RecyclerView
    private Paint p = new Paint();
    RecyclerView recyclerView;
    List<Movie> moviesList;
    Retrofit retrofit;
    MovieApi movieApi;
    MovieAdapter adapter;

    // Variables for Database and Saving Matches
    FirebaseAuth mAuth;
    DatabaseReference movieDb;
    String currentUid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        // Set up for saving matches
        mAuth = FirebaseAuth.getInstance();
        currentUid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        movieDb = FirebaseDatabase.getInstance().getReference().child("Users");


        recyclerView = findViewById(R.id.recyclerView);
        moviesList = new ArrayList<>();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://streaming-availability.p.rapidapi.com/search/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        movieApi = retrofit.create(MovieApi.class);


        Call<JSONResponse> call = movieApi.getMovies(generateRandomPage());
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                moviesList = new ArrayList<>(Arrays.asList(jsonResponse.getMovieList()));
                PutDataIntoRecyclerView(moviesList);
                enableSwipe();
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });
    }

    // generates a random page number. Will have to test the endpoints and use a switch case to
    // set the maximum page depending on service, genre etc.
    private Integer generateRandomPage() {
        int min = 1;
        int max = 200;
        return new Random().nextInt(max - min + 1);
    }

    private void PutDataIntoRecyclerView(List<Movie> moviesList) {
        adapter = new MovieAdapter(this, moviesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void enableSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            int count = 0;
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getBindingAdapterPosition();
                if (direction == ItemTouchHelper.LEFT){
                    final Movie deletedModel = moviesList.get(position);
                    final int deletedPosition = position;
                    adapter.removeItem(position);
                    // showing snack bar with Undo option
                    Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), "Not today!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // undo is selected, restore the deleted item
                            adapter.restoreItem(deletedModel, deletedPosition);
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                } else {
                    final Movie deletedModel = moviesList.get(position);

                    // Create a Movie object, save an ID and title into Database,
                    // Save the current UID so that we can compare with invited user(?)
                    Movie movieObj = new Movie();
                    String movieId = movieObj.getTmdbID();
                    String title = movieObj.getTitle();



                    final int deletedPosition = position;
                    adapter.removeItem(position);
                    // showing snack bar with Undo option
                    Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), "Hope we get a match!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // undo is selected, restore the deleted item
                            adapter.restoreItem(deletedModel, deletedPosition);
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }
                count++;
                if (count == 8) {
                    Call<JSONResponse> call = movieApi.getMovies(generateRandomPage());
                    call.enqueue(new Callback<JSONResponse>() {
                        @Override
                        public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                            JSONResponse jsonResponse = response.body();
                            moviesList = new ArrayList<>(Arrays.asList(jsonResponse.getMovieList()));
                            PutDataIntoRecyclerView(moviesList);
                            enableSwipe();
                            count = 0;
                        }

                        @Override
                        public void onFailure(Call<JSONResponse> call, Throwable t) {

                        }
                    });
                }

            }
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                    View itemView = viewHolder.itemView;
                    if(dX > 0){
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}