package com.example.moviematchv2;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class LobbyGuest extends AppCompatActivity {

    Intent intent;
    NavigationView navigationView;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    RecyclerView recyclerView;
    UserAdapter adapter;
    private String name;
    private String email;
    private String phone;
    private String ids;


    // Variables for Database and Reading Matches
    private FirebaseAuth mAuth;
    private DatabaseReference movieDb;
    private DatabaseReference userDb;
    private DatabaseReference matchesDb;
    private String currentUid;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private String userIds, userName, userPhone, userEmail;

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


        // MovieDB References
        movieDb = FirebaseDatabase.getInstance().getReference().child("Movies");
        DatabaseReference movieDB1 = FirebaseDatabase.getInstance().getReference().child("Movies").child("services");
        DatabaseReference netflixMovies = movieDB1.child("netflix");
        DatabaseReference primeMovies = movieDB1.child("prime");
        DatabaseReference disneyMovies = movieDB1.child("disney");
        matchesDb = FirebaseDatabase.getInstance().getReference().child("Matches");

        movieIdsList = new ArrayList<>();
        usersIdList = new ArrayList<>();
        userArrayList = new ArrayList<User>();


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