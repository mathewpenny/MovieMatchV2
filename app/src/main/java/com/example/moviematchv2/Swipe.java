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
import android.widget.ImageButton;
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
import com.google.firebase.database.Query;
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
    public static List<Movie> moviesList;
    private Retrofit retrofit;
    private MovieApi movieApi;
    private MovieAdapter adapter;
    private String chosenStreaming;
    private ImageButton refresh;
    private int chosenGenre, position;
    private String chosenType;
    private Intent intent;
    NavigationView navigationView;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private ArrayList<String> matchMovies;

    // Variables for Database and Saving Matches
    private FirebaseAuth mAuth;
    private DatabaseReference movieDb;
    private DatabaseReference userDb;
    private DatabaseReference matchedUserDb;
    private String currentUid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
        matchMovies = new ArrayList<>();

        refresh = findViewById(R.id.refreshBtn);

        drawerLayout = findViewById(R.id.linearLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        refresh.setOnClickListener(view -> {
            Call<JSONResponse> call = movieApi.getMovies(generateRandomPage(), chosenStreaming, chosenType, chosenGenre);
            call.enqueue(new Callback<JSONResponse>() {
                @Override
                public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                    JSONResponse jsonResponse = response.body();
                    if (jsonResponse != null) {
                        moviesList = new ArrayList<>(Arrays.asList(jsonResponse.getMovieList()));
                        PutDataIntoRecyclerView(moviesList);
                        enableSwipe();
                    }
                }
                @Override
                public void onFailure(Call<JSONResponse> call, Throwable t) {
                }
            });
        });

        navigationView = findViewById(R.id.drawer_view);
        navigationView.setNavigationItemSelectedListener(item -> {
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
        });

        // Set up for saving matches
        mAuth = FirebaseAuth.getInstance();
        currentUid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        movieDb = FirebaseDatabase.getInstance().getReference().child("Movies");
        userDb = FirebaseDatabase.getInstance().getReference().child("Users");
        matchedUserDb = FirebaseDatabase.getInstance().getReference().child("Users");
        matchedUserDb = userDb.child(currentUid);

        // Get intent from HostActivity to set up API call by choice of streaming service and genre
        intent = getIntent();
        chosenType = intent.getStringExtra("type");
        chosenStreaming = intent.getStringExtra("streaming");
        chosenGenre = intent.getIntExtra("genre", 0);

        // Set up for showing movies in recyclerview
        recyclerView = findViewById(R.id.recyclerView);
        moviesList = new ArrayList<>();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://streaming-availability.p.rapidapi.com/search/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        movieApi = retrofit.create(MovieApi.class);

        Call<JSONResponse> call = movieApi.getMovies(generateRandomPage(), chosenStreaming, chosenType, chosenGenre);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                Log.e("RANDOM_PAGE", "" + generateRandomPage());
                if(jsonResponse != null) {
                    moviesList = new ArrayList<>(Arrays.asList(jsonResponse != null ? jsonResponse.getMovieList() : new Movie[0]));
                    PutDataIntoRecyclerView(moviesList);
                    enableSwipe();

                } else {
                    Toast.makeText(Swipe.this, "Oh snap! We had a problem, try again please!", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
            }
        });
        registerForContextMenu(recyclerView);
    }


    @NonNull
    private Integer generateRandomPage() {
        Random random = new Random();
        int randomPage = 0;
        while (chosenStreaming.equals("netflix") && chosenType.equals("movies")) {
            switch (chosenGenre) {
                case 28:
                    randomPage = random.nextInt(57); // Action
                    break;
                case 12:
                    randomPage = random.nextInt(39); //Adventure
                    break;
                case 16:
                    randomPage = random.nextInt(17); // Animation
                    break;
                case 1:
                    randomPage = random.nextInt(27); // Biography
                    break;
                case 35:
                    randomPage = random.nextInt(134); // Comedy
                    break;
                case 80:
                    randomPage = random.nextInt(60); // Crime
                    break;
                case 99:
                    randomPage = random.nextInt(79); // Documentary
                    break;
                /*case 18:
                    max = 174; // Drama
                    break;
                case 10751:
                    max = 31; // Family
                    break;
                case 14:
                    max = 22; // Fantasy
                    break;
                case 27:
                    max = 75; // Horror
                    break;
                case 4:
                    max = 5; // Musical
                    break;
                case 10764:
                    max = 2; // Reality
                    break;
                case 10749:
                    max = 49; // Romance
                    break;
                case 878:
                    max = 30; // Sci Fi
                    break;
                case 53:
                    max = 93; // Thriller

                    break;
                case 37:
                    max = 10; // Western
                    randomPage = new Random().nextInt(max - min + 1);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + chosenGenre);
            }
        } else if (chosenStreaming.equals("prime") && chosenType.equals("movies")) {
            switch (chosenGenre) {
                case 28:
                    max = 38; // Action
                    break;
                case 12:
                    max = 63; // Adventure
                    break;
                case 16:
                    max = 23; // Animation
                    break;
                case 1:
                    max = 12; // Biography
                    break;
                case 35:
                    max = 72; // Comedy
                    break;
                case 80:
                    max = 16; // Crime
                    break;
                case 99:
                    max = 22; // Documentary
                    break;
                case 18:
                    max = 50; // Drama
                    break;
                case 10751:
                    max = 47; // Family
                    break;
                case 14:
                case 10749:
                    max = 17; // Fantasy
                    break;
                case 27:
                    max = 6; // Horror
                    break;
                case 4:
                    max = 3; // Musical
                    break;
                case 10764:
                    max = 2; // Reality
                    break;
                case 878:
                    max = 14; // Sci Fi
                    break;
                case 53:
                    max = 11; // Thriller
                    break;
                case 37:
                    max = 2; // Western
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + chosenGenre);
            }
        } else if (chosenStreaming.equals("disney") && chosenType.equals("movies")) {
            switch (chosenGenre) {
                case 28:
                    max = 38; // Action
                    break;
                case 12:
                    max = 63; // Adventure
                    break;
                case 16:
                    max = 23; // Animation
                    break;
                case 1:
                    max = 12; // Biography
                    break;
                case 35:
                    max = 72; // Comedy
                    break;
                case 80:
                    max = 16; // Crime
                    break;
                case 99:
                    max = 22; // Documentary
                    break;
                case 18:
                    max = 50; // Drama
                    break;
                case 10751:
                    max = 47; // Family
                    break;
                case 14:
                case 10749:
                    max = 17; // Fantasy & Romance
                    break;
                case 27:
                    max = 6; // Horror
                    break;
                case 4:
                    max = 3; // Musical
                    break;
                case 10764:
                    max = 56; // Reality
                    break;
                case 878:
                    max = 14; // Sci Fi
                    break;
                case 53:
                    max = 11; // Thriller
                    break;
                case 37:
                    max = 2; // Western
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + chosenGenre);
            }
        }
        if (chosenStreaming.equals("netflix") && chosenType.equals("series")) {
            switch (chosenGenre) {
                case 28:
                    max = 20; // Action
                    break;
                case 12:
                    max = 23; // Adventure
                    break;
                case 16:
                    max = 28; // Animation
                    break;
                case 1:
                case 14:
                case 27:
                    max = 5; // Biography
                    break;
                case 35:
                    max = 31; // Comedy
                    break;
                case 80:
                    max = 22; // Crime
                    break;
                case 99:
                    max = 30; // Documentary
                    break;
                case 18:
                    max = 33; // Drama
                    break;
                case 10751:
                    max = 13;  // & Family
                    break;
                case 4:
                case 37:
                    max = 2; // Musical
                    break;
                case 10764:
                    max = 14; // Reality
                    break;
                case 878:
                case 53:
                    max = 4; // Sci Fi
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + chosenGenre);
            }
        } else if (chosenStreaming.equals("prime") && chosenType.equals("series")) {
            switch (chosenGenre) {
                case 28:
                case 10764:
                case 878:
                    max = 12; // Action
                    break;
                case 12:
                    max = 13; // Adventure
                    break;
                case 16:
                    max = 11; // Animation
                    break;
                case 1:
                case 27:
                    max = 4; // Biography
                    break;
                case 35:
                    max = 22; // Comedy
                    break;
                case 80:
                    max = 17; // Crime
                    break;
                case 99:
                    max = 21; // Documentary
                    break;
                case 18:
                    max = 38; // Drama
                    break;
                case 10751:
                    max = 9; // Family
                    break;
                case 14:
                    max = 7;
                    break;
                case 10749:
                case 4:
                    max = 1; // Fantasy
                    break;
                case 53:
                    max = 6; // Thriller
                    break;
                case 37:
                    max = 2;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + chosenGenre);
            }
        } else if (chosenStreaming.equals("disney") && chosenType.equals("series")) {
            switch (chosenGenre) {
                case 28:
                    max = 14; // Action
                    break;
                case 12:
                case 16:
                    max = 18; // Adventure
                    break;
                case 1:
                    max = 3; // Biography
                    break;
                case 35:
                case 18:
                    max = 21; // Comedy
                    break;
                case 80:
                    max = 8; // Crime
                    break;
                case 99:
                    max = 12; // Documentary
                    break;
                case 10751:
                    max = 13; // Family
                    break;
                case 14:
                case 878:
                    max = 4;
                    break;
                case 27:
                case 4:
                    max = 1; // Horror
                    break;
                case 10764:
                    max = 6; // Reality
                    break;
                case 53:
                    max = 2; // Thriller
                    randomPage = random.nextInt(max);
                    randomPage += 1;
                break;
                default:
                    throw new IllegalStateException("Unexpected value: " + chosenGenre); */

            }
        }
                return randomPage;
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

                    Query movieIdQuery = movieDb.child("services").child(chosenStreaming).orderByKey().startAt(movieId).endAt(movieId);
                    movieIdQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                           if(iterator.hasNext()) {
                              DataSnapshot infoSnap = iterator.next();
                               boolean userExists = false;
                               ArrayList<String> userIdList = new ArrayList<String>();
                               for(DataSnapshot childList : infoSnap.getChildren()) {
                                   for(DataSnapshot idList : childList.getChildren()) {
                                       if (idList.getKey().equals("userId")) {
                                           String matchUserId = (String) idList.getValue();
                                           if (!matchUserId.equals(currentUid)) {
                                               userIdList.add(matchUserId);
                                           } else {
                                               userExists = true;
                                           }
                                       }
                                   }
                               } // Now, check if user is already existing in the list, if no, add to list
                               if(!userExists) {
                                   Log.e("USER_EXISTS", "" + userExists);
                                   movieDb.child("services").child(chosenStreaming).child(movieId).push().child("userId").setValue(currentUid);
                                   userDb.child(currentUid).child("connections").child(chosenStreaming).push().child("movieId").setValue(movieId);
                               }
                               // Then check if List is empty, If not empty, process user list to show matches in recyclerview
                               if(userIdList.size() == 0) {
                                   Toast.makeText(Swipe.this, "Still...no matches yet! Keep trying!", Toast.LENGTH_SHORT).show();
                               } else {
                                   Toast.makeText(Swipe.this, "You have MATCHED! with " + userIdList.size() + " other people! Look at you, having good taste in movies!", Toast.LENGTH_SHORT).show();

                                   // Launch the LobbyGuest Activity to pass the User Details from the Users table and project that into RecyclerView
                                   intent = new Intent(Swipe.this, LobbyGuest.class);
                                   intent.putExtra("movieTitle", movieTitle);
                                   intent.putStringArrayListExtra("PASS_MATCHES", userIdList);
                                   startActivity(intent);
                               }
                           } else {
                               Log.e("MOVIE_EXISTS", "" + movieId);
                               // here we want to add movie Id, title and user id to the database and tell them that they
                               // have bad taste in movies aka no matches :(
                               movieDb.child("services").child(chosenStreaming).child(movieId).push().child("movieTitle").setValue(movieTitle);
                               movieDb.child("services").child(chosenStreaming).child(movieId).push().child("userId").setValue(currentUid);
                               userDb.child(currentUid).child("connections").child(chosenStreaming).push().child("movieId").setValue(movieId);
                               Toast.makeText(Swipe.this, "No matches yet! Keep trying!", Toast.LENGTH_SHORT).show();
                           }
                           // stops the listener so that it doesn't push the user again.
                           movieIdQuery.removeEventListener(this);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    final int deletedPosition = position;
                    adapter.removeItem(position);
                    Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), "Hope we get a match!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", view -> adapter.restoreItem(deletedModel, deletedPosition));
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }
                count++;

                if (count == 8) {
                    Call<JSONResponse> call = movieApi.getMovies(generateRandomPage(), chosenStreaming, chosenType, chosenGenre);
                    call.enqueue(new Callback<JSONResponse>() {
                        @Override
                        public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                            JSONResponse jsonResponse = response.body();
                            if(jsonResponse != null) {
                                moviesList = new ArrayList<>(Arrays.asList(jsonResponse != null ? jsonResponse.getMovieList() : new Movie[0]));
                                PutDataIntoRecyclerView(moviesList);
                                enableSwipe();
                            } else {
                                Toast.makeText(Swipe.this, "Oh snap! We had a problem, try again please!", Toast.LENGTH_LONG).show();
                                jsonResponse = response.body();
                                moviesList = new ArrayList<>(Arrays.asList(jsonResponse != null ? jsonResponse.getMovieList() : new Movie[0]));
                                PutDataIntoRecyclerView(moviesList);
                                enableSwipe();
                            }
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
        Call<JSONResponse> call = movieApi.getMovies(generateRandomPage(), String.valueOf(item), chosenType, chosenGenre); //new
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                if(jsonResponse != null) {
                    moviesList = new ArrayList<>(Arrays.asList(jsonResponse != null ? jsonResponse.getMovieList() : new Movie[0]));
                    PutDataIntoRecyclerView(moviesList);
                    enableSwipe();
                } else {
                    Toast.makeText(Swipe.this, "Oh snap! We had a problem, try again please!", Toast.LENGTH_LONG).show();
                    jsonResponse = response.body();
                    moviesList = new ArrayList<>(Arrays.asList(jsonResponse != null ? jsonResponse.getMovieList() : new Movie[0]));
                    PutDataIntoRecyclerView(moviesList);
                    enableSwipe();
                }
            }
            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void details(View view) {
        position =  recyclerView.getChildAdapterPosition(view);
        Intent intent = new Intent(getApplicationContext(), Details.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(Swipe.this, LobbyHost.class);
        startActivity(intent);
        finish();
    }
}