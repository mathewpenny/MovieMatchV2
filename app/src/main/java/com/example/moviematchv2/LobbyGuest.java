package com.example.moviematchv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
    TextView movieTitleRV;

    // Variables for Database and Reading Matches
    private FirebaseAuth mAuth;
    private DatabaseReference userDb;
    private String currentUid;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    private ArrayList<String> potentialMatch;
    private ArrayList<User> matchPeople;
    private String movieTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_guest);

        movieTitleRV = findViewById(R.id.movieTitleTxt);

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
        matchPeople = new ArrayList<>();

        intent = getIntent();
        movieTitle = intent.getStringExtra("movieTitle");  // list of movie titles
        potentialMatch = intent.getStringArrayListExtra("PASS_MATCHES"); // list of user ID
        movieTitleRV.setText(movieTitle);

        Query userDbQuery = userDb.orderByKey();
        userDbQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot user : snapshot.getChildren()) {
                    if(potentialMatch.contains(user.getKey())) {
                        user.getValue();
                        User matched = new User();
                        matched.setUserName((String) user.child("name").getValue());
                        matched.setUserPhone((String) user.child("phone").getValue());
                        matchPeople.add(matched);
                    }
                }
                userDbQuery.removeEventListener(this);
                PutDataIntoRecyclerView(matchPeople);
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
        Intent intent = new Intent(LobbyGuest.this, Swipe.class);
        startActivity(intent);
        finish();
    }
}