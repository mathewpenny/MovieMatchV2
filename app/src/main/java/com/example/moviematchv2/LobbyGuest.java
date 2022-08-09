package com.example.moviematchv2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
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
    private String currentUid, movieTitle, movieId, chosenStreaming, name, externalLaunchLink;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private ImageButton playBtn, shareBtn;
    private ArrayList<String> potentialMatch;
    private ArrayList<User> matchPeople;

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

        playBtn = findViewById(R.id.playButton);
        shareBtn = findViewById(R.id.shareButton);

        navigationView = findViewById(R.id.drawer_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if(id == R.id.WelcomePage) {
                intent = new Intent(getApplicationContext(), WelcomePage.class);
                startActivity(intent);
                finish();
            }
            else if (id == R.id.AccountLobby) {
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
        intent = getIntent();

        chosenStreaming = intent.getStringExtra("chosenStreaming"); // streaming platform for share and play button
        movieId = intent.getStringExtra("movieId"); // list of movie ids
        movieTitle = intent.getStringExtra("movieTitle");  // list of movie titles
        potentialMatch = intent.getStringArrayListExtra("PASS_MATCHES"); // list of user ID
        name = intent.getStringExtra("name");
        externalLaunchLink = intent.getStringExtra("link");
        movieTitleRV.setText(movieTitle);


        currentUid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        userDb = FirebaseDatabase.getInstance().getReference().child("Users");
        matchPeople = new ArrayList<>();

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

            shareBtn.setOnClickListener(view -> {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = "Hey! I just found a person to watch " + movieTitle + " on Movie Match!!";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, "Share using"));
            });

            playBtn.setOnClickListener(view -> {
                switch (chosenStreaming) {
                    case "netflix":
                        try {
                            Intent launchIntent = new Intent(Intent.ACTION_VIEW);
                            launchIntent.setClassName("com.netflix.mediaclient", "com.netflix.mediaclient.ui.launch.UIWebViewActivity");
                            launchIntent.setData(Uri.parse(externalLaunchLink));
                            startActivity(launchIntent);
                        } catch (Exception e) {
                            Intent launchIntent = new Intent(Intent.ACTION_VIEW);
                            launchIntent.setData(Uri.parse(externalLaunchLink));
                            startActivity(launchIntent);
                        }
                        break;
                    case "prime":
                        try {
                            Intent launchIntent = new Intent(Intent.ACTION_VIEW);
                            launchIntent.setClassName("com.amazon.firebat", "com.amazon.firebat.deeplink.DeepLinkRoutingActivity");
                            launchIntent.setData(Uri.parse(externalLaunchLink));
                            startActivity(launchIntent);
                        } catch (Exception e) {
                            Intent launchIntent = new Intent(Intent.ACTION_VIEW);
                            launchIntent.setData(Uri.parse(externalLaunchLink));
                            startActivity(launchIntent);
                        }
                        break;
                    case "disney":
                        try {
                            Intent launchIntent = new Intent(Intent.ACTION_VIEW);
                            launchIntent.setClassName("com.disney.disneyplus", "com.bamtechmedia.dominguez.main.MainActivity");
                            launchIntent.setData(Uri.parse(externalLaunchLink));
                            startActivity(launchIntent);
                        } catch (Exception e) {
                            Intent launchIntent = new Intent(Intent.ACTION_VIEW);
                            launchIntent.setData(Uri.parse(externalLaunchLink));
                            startActivity(launchIntent);
                        }
                        break;
                }
            });

    }


        public void sendText(View view){
            String phone = matchPeople.get(adapter.getPosition()).getUserPhone();
            String nameFriend = matchPeople.get(adapter.getPosition()).getUserName();
            Uri uri = Uri.parse("smsto:" + phone);
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            String message = ("Hello " + nameFriend + ". My name is " + name +  ". We just matched on the application Movie Match for the following movie: " + movieTitle + ". Would you like to watch it together?" );
            intent.putExtra("sms_body", "" + message);
            startActivity(intent);
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