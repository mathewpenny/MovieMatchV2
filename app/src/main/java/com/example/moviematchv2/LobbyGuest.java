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
    private DatabaseReference matchesDb;
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
        navigationView.setNavigationItemSelectedListener(item -> {
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

        currentUid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        userDb = FirebaseDatabase.getInstance().getReference().child("Users");
        DatabaseReference currentUserDb = userDb.child(currentUid);
        DatabaseReference currentUserDbConnect = currentUserDb.child("connections");
        DatabaseReference currentUserDbConnectServices = currentUserDbConnect.child("services");
        DatabaseReference netflixMatches = currentUserDbConnectServices.child("netflix");
        DatabaseReference netflixMatchesYup = netflixMatches.child("yup");
        DatabaseReference netflixMatchesYupIDs = netflixMatchesYup.child("movieId");

        // MovieDB References
        movieDb = FirebaseDatabase.getInstance().getReference().child("Movies");
        DatabaseReference movieDB1 = FirebaseDatabase.getInstance().getReference().child("Movies").child("services");
        DatabaseReference movieDB2 = movieDB1.child("netflix");

        matchesDb = FirebaseDatabase.getInstance().getReference().child("Matches");

        movieIdsList = new ArrayList<>();
        usersIdList = new ArrayList<>();
        userArrayList = new ArrayList<User>();


        netflixMatchesYupIDs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                    while (iterator.hasNext()) {
                        snapshot = iterator.next();
                        String movieIds = (String) snapshot.getValue();
                        movieIdsList.add(movieIds);
                        Log.e("NETFLIX_YUPS", "" + movieIdsList);
                    }
                }
                movieDB2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            DatabaseReference movieDb3 = movieDB2.child("yup");
                            movieDb3.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    // LIST ACCESSIBLE HERE
                                    DatabaseReference movieDb4 = null;
                                    for (int i = 0; i < movieIdsList.size(); i++) {
                                        String idToRead = movieIdsList.get(i);
                                        movieDb4 = movieDb3.child(idToRead);
                                    }

                                    DatabaseReference movieDb5 = movieDb4.child("userId");

                                    movieDb5.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Log.e("MOVIE5_SNAP?", "" + snapshot); // this snapshot holds all users and their info that have swiped on a movie

                                            Iterable<DataSnapshot> snapIter = snapshot.getChildren();
                                            Iterator<DataSnapshot> iterator = snapIter.iterator();

                                            // have to loop thru and get ids, name and phone
                                            while (iterator.hasNext()) { // while the iterator has children to look thru
                                                DataSnapshot snapNext = (DataSnapshot) iterator.next();
                                                Log.e("SNAP_NEXT", ""+snapNext); // snapNext goes thru the user tree one by one

                                                String userIds = (String) snapNext.child("userIds").getValue(); // works, gets id
                                                Log.e("GETTING_USERIDS", ""+ userIds);
                                                iterator.next();
                                                String userNames = (String) snapNext.child("userNames").getValue();
                                                Log.e("GETTING_USERNAMES", ""+ userNames);

                                          /*
                                                matchesDb.child("userIds").push().child("userId").setValue(userIds);
                                                usersIdList.add(userIds);
*/
                                                matchesDb.child("userIds").addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        Iterable<DataSnapshot> snapIter = snapshot.getChildren();
                                                        Iterator<DataSnapshot> iterator = snapIter.iterator();

                                                        while (iterator.hasNext()) {
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



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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
                usersIdList.clear();
                Intent intent = new Intent(LobbyGuest.this, WelcomePage.class);
                startActivity(intent);
                finish();
            }
        }