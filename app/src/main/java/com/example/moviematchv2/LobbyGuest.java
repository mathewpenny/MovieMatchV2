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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.drawer_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) { //this is the item in the menu that was selected
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
            }
        });

            // get a reference of the userDB holding the movieIds so we have a key and a movie id. then reference the moviesDb, compare the ids and
            // return all other userIds and then display the names and phone numbers in the recyclerView on Matches activity
            currentUid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
            userDb = FirebaseDatabase.getInstance().getReference(); //.child("Users"); //.child(currentUid).child("connections").child("services").child("netflix").child("movieId");



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