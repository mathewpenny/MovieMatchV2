package com.example.moviematchv2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


public class LobbyGuest extends AppCompatActivity {

    Intent intent;
    NavigationView navigationView;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    RecyclerView recyclerView;
    UserAdapter adapter;

    // Variables for Database and Reading Matches
    private FirebaseAuth mAuth;
    private DatabaseReference movieDb;
    private DatabaseReference userDb;
    private String currentUid;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;


    ArrayList<String> usersIdList;
    ArrayList<String> matchMovies;
    ArrayList<String> movieIdsList;
    ArrayList<User> userArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_guest);

        recyclerView = findViewById(R.id.recyclerViewMatches);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawer_view2);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        matchMovies = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.drawer_view);
        navigationView.setNavigationItemSelectedListener(item -> { //this is the item in the menu that was selected
            int id = item.getItemId();

            if (id == R.id.AccountLobby) {
                intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            } else if (id == R.id.Instructions) {
                intent = new Intent(getApplicationContext(), FAQ.class);
                startActivity(intent);
                finish();
            } else if (id == R.id.Logout) {
                // Firebase Sign Out
                mAuth.signOut();
                // Google Sign out
                gsc.signOut();
                // Facebook Sign Out
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(LobbyGuest.this, Login.class);
                startActivity(intent);
                finish();
            }
            return false;
        });

            // get a reference of the userDB holding the movieIds so we have a key and a movie id. then reference the moviesDb, compare the ids and
            // return all other userIds and then display the names and phone numbers in the recyclerView on Matches activity
            currentUid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
            userDb = FirebaseDatabase.getInstance().getReference().child("Users");
            DatabaseReference matchDb =  FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid);
            DatabaseReference matchDbAgain = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("connections");
            DatabaseReference matchDbAgain1 = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("connections").child("services");
            DatabaseReference netflixMatches = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("connections").child("services").child("netflix");
            DatabaseReference netflixMatchesYup = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("connections").child("services").child("netflix").child("yup");
            DatabaseReference netflixMatchesYupIDs = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("connections").child("services").child("netflix").child("yup").child("movieId");

            movieIdsList = new ArrayList<>();
            usersIdList = new ArrayList<>();
            userArrayList = new ArrayList<User>();


        netflixMatchesYupIDs.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.e("SNAPSHOT", "" + snapshot);
                        if(snapshot.hasChildren()) {
                            Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                            while(iterator.hasNext()) {
                                snapshot = iterator.next();
                                String movieIds = (String) snapshot.getValue();
                                movieIdsList.add(movieIds);
                                Log.e("MOVIE_IDS_FROM_USER",  movieIds);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            // MovieDB References
            movieDb = FirebaseDatabase.getInstance().getReference().child("Movies");
            DatabaseReference movieDB1 = FirebaseDatabase.getInstance().getReference().child("Movies").child("services");
            DatabaseReference movieDB2 = movieDB1.child("netflix");

        movieDB2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    DatabaseReference movieDb3 = movieDB2.child("yup");
                    movieDb3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(int i = 0; i < movieIdsList.size(); i++) {
                                String idToRead = movieIdsList.get(i);
                                DatabaseReference movieDb4 = movieDb3.child(idToRead);
                               // Log.e("MOVIE_DB_4", "" + movieDb4);

                                DatabaseReference movieDb5 = movieDb4.child("userId");
                                movieDb5.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            Iterable<DataSnapshot> snapIter = snapshot.getChildren();
                                            Iterator<DataSnapshot> iterator = snapIter.iterator();

                                            while(iterator.hasNext()) {
                                                DataSnapshot snapNext = (DataSnapshot) iterator.next();
                                                Log.e("SNAPNEXT", "" + snapNext);
                                                String userIds = (String) snapNext.child("userIds").getValue();
                                                Log.e("USER_IDS", "" + userIds);
                                                if(!userIds.equals(currentUid))
                                                usersIdList.add(userIds);
                                                Log.e("USER_IDS_FROM_MOVIES", "" + usersIdList);

                                            userDb.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    Iterable<DataSnapshot> snapIter = snapshot.getChildren();
                                                    Iterator<DataSnapshot> iterator = snapIter.iterator();

                                                    while(iterator.hasNext()) {
                                                        DataSnapshot snapNext = (DataSnapshot) iterator.next();
                                                        String name = (String) snapNext.child("name").getValue();
                                                        User user = new User(name);
                                                        userArrayList.add(user);
                                                        PutDataIntoRecyclerView(userArrayList);
                                                    }
                                                }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



        PutDataIntoRecyclerView(userArrayList);
    }


        private void PutDataIntoRecyclerView(List<User> usersList) {
                adapter = new UserAdapter(this, usersList);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);
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
                Intent intent = new Intent(LobbyGuest.this, WelcomePage.class);
                startActivity(intent);
                finish();
            }
        }