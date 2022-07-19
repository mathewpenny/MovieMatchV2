package com.example.moviematchv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class LobbyAccount extends AppCompatActivity {

    private ImageButton updateBtn, deleteBtn, reviewBtn, logoutBtn;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private FirebaseAuth mAuth;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_account);

            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            gsc = GoogleSignIn.getClient(this, gso);
            mAuth = FirebaseAuth.getInstance();

            logoutBtn = (ImageButton) findViewById(R.id.logoutButton);
            updateBtn = (ImageButton) findViewById(R.id.updateButton);

            drawerLayout = findViewById(R.id.drawer_view2);
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            navigationView = findViewById(R.id.drawer_view);
            navigationView.setNavigationItemSelectedListener(item -> { //this is the item in the menu that was selected
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
                    Intent intent = new Intent(LobbyAccount.this, Login.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            });

            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LobbyAccount.this, UpdateAccount.class);
                    startActivity(intent);
                    finish();
                }
            });

            logoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Firebase Sign Out
                    mAuth.signOut();
                    // Google Sign out
                    gsc.signOut();
                    // Facebook Sign Out
                    LoginManager.getInstance().logOut();
                    Intent intent = new Intent(LobbyAccount.this, Login.class);
                    startActivity(intent);
                    finish();
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
            Intent intent = new Intent(LobbyAccount.this, WelcomePage.class);
            startActivity(intent);
            finish();
    }
}

