package com.example.moviematchv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class WelcomePage extends AppCompatActivity {

    private ImageButton matchesBtn, searchBtn, accountBtn;
    private TextView name;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;

    private FirebaseUser user;
    private String userName, userId;
    Intent intent;
    NavigationView navigationView;
    private FirebaseAuth mAuth;
    private DatabaseReference userDb;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        drawerLayout = findViewById(R.id.drawer_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.drawer_view2);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) { //this is the item in the menu that was selected
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
                    Intent intent = new Intent(WelcomePage.this, Login.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });

            searchBtn = (ImageButton) findViewById(R.id.swipeButton);
            matchesBtn = (ImageButton) findViewById(R.id.matchButton);
            accountBtn = (ImageButton) findViewById(R.id.accountButton);
            name = (TextView) findViewById(R.id.nameText);

            user = FirebaseAuth.getInstance().getCurrentUser();

            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            gsc = GoogleSignIn.getClient(this, gso);


            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            if (account != null) {
                userName = account.getDisplayName();
                name.setText(userName);
            } else if (user != null) {
                getUserName();
            }

     /*   // This is where the app gets hung up. Null object Reference on fullName
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try {
                            String fullName = object.getString("name");
                            name.setText(fullName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link");
        request.setParameters(parameters);
        request.executeAsync();
*/

            searchBtn.setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(), LobbyHost.class);
                startActivity(intent);
                finish();
            });

            matchesBtn.setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(), LobbyGuest.class);
                startActivity(intent);
                finish();
            });

            accountBtn.setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(), LobbyAccount.class);
                startActivity(intent);
                finish();
            });
        }

    // Outside of onCreate() starts here
    private void getUserName() {
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                    if(map.get("name") != null) {
                        userName = map.get("name").toString();
                        name.setText(userName);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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
            Intent intent = new Intent(WelcomePage.this, LandingPage.class);
            startActivity(intent);
            finish();
    }
}