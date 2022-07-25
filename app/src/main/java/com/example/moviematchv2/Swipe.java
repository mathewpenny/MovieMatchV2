package com.example.moviematchv2;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
    private RecyclerView recyclerView;
    private List<Movie> moviesList;
    private Retrofit retrofit;
    private MovieApi movieApi;
    private MovieAdapter adapter;
    private String chosenStreaming;
    private Button seeMatches;
    private int chosenGenre;
    private Intent intent;
    NavigationView navigationView;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    ArrayList<String> matchMovies;


    // Variables for Database and Saving Matches
    private FirebaseAuth mAuth;
    private DatabaseReference movieDb;
    private DatabaseReference userDb;
    private DatabaseReference matchedUserDb;
    private String currentUid;
    private String compareUserIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
        matchMovies = new ArrayList<>();

        seeMatches = findViewById(R.id.seeMatches);
        drawerLayout = findViewById(R.id.linearLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //MAyBE NEED THIS< MAYBE NOT
        seeMatches.setOnClickListener(view -> {
            intent = new Intent(Swipe.this, LobbyGuest.class);
            intent.putStringArrayListExtra("matches", matchMovies);
            startActivity(intent);
            finish();
        });


        navigationView = findViewById(R.id.drawer_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.AccountLobby) {
                    intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                }else if(id == R.id.Instructions){
                    intent = new Intent(getApplicationContext(), FAQ.class);
                    startActivity(intent);
                    finish();
                }else if(id == R.id.Logout){
                    // Firebase Sign Out
                    mAuth.signOut();
                    // Google Sign out
                    gsc.signOut();
                    // Facebook Sign Out
                    LoginManager.getInstance().logOut();

                    Intent intent = new Intent(Swipe.this, Login.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });

            // Set up for saving matches
            mAuth = FirebaseAuth.getInstance();
            currentUid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
            movieDb = FirebaseDatabase.getInstance().getReference().child("Movies");
            userDb = FirebaseDatabase.getInstance().getReference().child("Users");
            matchedUserDb = FirebaseDatabase.getInstance().getReference().child("Users");


            // Get intent from HostActivity to set up API call by choice of streaming service and genre
            intent = getIntent();
            chosenStreaming = intent.getStringExtra("streaming");
            chosenGenre = intent.getIntExtra("genre", 0);
            Log.e("CHOSENGENRE", "" + chosenGenre);

            // Set up for showing movies in recyclerview
            recyclerView = findViewById(R.id.recyclerView);
            moviesList = new ArrayList<>();
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://streaming-availability.p.rapidapi.com/search/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            movieApi = retrofit.create(MovieApi.class);

            Call<JSONResponse> call = movieApi.getMovies(generateRandomPage(), chosenStreaming, chosenGenre);
            call.enqueue(new Callback<JSONResponse>() {
                @Override
                public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                    JSONResponse jsonResponse = response.body();
                        moviesList = new ArrayList<>(Arrays.asList(jsonResponse != null ? jsonResponse.getMovieList() : new Movie[0]));
                        PutDataIntoRecyclerView(moviesList);
                        enableSwipe();
                }
                @Override
                public void onFailure(Call<JSONResponse> call, Throwable t) {
                }
            });
            registerForContextMenu(recyclerView);
        }
    // generates a random page number. Will have to test the endpoints and use a switch case to
    // set the maximum page depending on service, genre etc.
    private Integer generateRandomPage() {
        int min = 1;
        int max = 2;
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

                    Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), "Not today!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", view ->
                            adapter.restoreItem(deletedModel, deletedPosition));
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                } else {
                    // if swiped yes
                    final Movie deletedModel = moviesList.get(position);

                    String movieId = deletedModel.getTmdbID();
                    String movieTitle = deletedModel.getTitle();
                    String userId = mAuth.getCurrentUser().getUid();

                    Log.e("MOVIE", ""+movieId);
                    Log.e("CURRENTUSER", ""+userId);

                        movieDb.child("services").child(chosenStreaming).child("yup").child(movieId).child("userId").push().setValue(userId);
                        userDb.child(currentUid).child("connections").child("services").child(chosenStreaming).child("yup").child("movieId").push().setValue(movieId);


                    DatabaseReference movieDeepDive = movieDb.child("services").child(chosenStreaming).child("yup").child(movieId);
                    movieDeepDive.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.getValue() != null) {
                                for(DataSnapshot movieSnapshot : snapshot.getChildren()) {
                                    compareUserIds = "" + movieSnapshot.getValue();

                                    Log.e("USERIDS", ""+ compareUserIds);

                                    if(movieSnapshot.getChildrenCount() > 1) {
                                        Toast.makeText(Swipe.this, "Match Made! We can watch " + movieTitle, Toast.LENGTH_SHORT).show();
                                        matchMovies.add(movieTitle);
                                        if(movieSnapshot.hasChildren()) {
                                            Iterator<DataSnapshot> iterator = movieSnapshot.getChildren().iterator();
                                            while(iterator.hasNext()) {
                                                matchedUserDb.child(currentUid).child("connections").child("services").child(chosenStreaming).child("yup").child("userId");
                                                movieSnapshot = iterator.next();
                                                String userNodeId = movieSnapshot.getKey();
                                                String matchedIds = (String) movieSnapshot.getValue();
                                                Toast.makeText(Swipe.this, "We can watch " + movieTitle + " with " + matchedIds, Toast.LENGTH_SHORT).show();
                                                Log.e("MATCHEDIDS", "" + matchedIds + " with key " + userNodeId);
                                            }
                                        }
                                        Log.e("Matched", "" + matchMovies);
                                    }
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                    Log.e("Matched", "" + matchMovies);
                    final int deletedPosition = position;
                    adapter.removeItem(position);
                    Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), "Hope we get a match!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            adapter.restoreItem(deletedModel, deletedPosition);
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }
                count++;
                if (count == 8) {
                    Call<JSONResponse> call = movieApi.getMovies(generateRandomPage(), chosenStreaming, chosenGenre);
                    call.enqueue(new Callback<JSONResponse>() {
                        @Override
                        public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                            JSONResponse jsonResponse = response.body();
                            moviesList = new ArrayList<>(Arrays.asList(jsonResponse != null ? jsonResponse.getMovieList() : new Movie[0]));
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

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int clickedItemPosition = item.getOrder();
        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Details.class);
        startActivity(intent);
        return true;
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
        moviesList.clear();
        Intent intent = new Intent(Swipe.this, WelcomePage.class);
        startActivity(intent);
        finish();
    }
}