package com.example.moviematchv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class LobbyAccount extends AppCompatActivity {

    private ImageButton updateBtn, deleteBtn, logoutBtn, settingsBtn;
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

            logoutBtn = findViewById(R.id.logoutButton);
            updateBtn =  findViewById(R.id.updateButton);
            deleteBtn = findViewById(R.id.deleteButton);
            settingsBtn = findViewById(R.id.settingsButton);


            drawerLayout = findViewById(R.id.drawer_view2);
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            navigationView = findViewById(R.id.drawer_view);
            navigationView.setNavigationItemSelectedListener(item -> { //this is the item in the menu that was selected
                int id = item.getItemId();
                if(id == R.id.welcomePage) {
                    intent = new Intent(getApplicationContext(), WelcomePage.class);
                    startActivity(intent);
                    finish();
                }
                else if (id == R.id.accountLobby) {
                    intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                }else if(id == R.id.instructions){
                    intent = new Intent(getApplicationContext(), FAQ.class);
                    startActivity(intent);
                    finish();
                }else if(id == R.id.logout){
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

            updateBtn.setOnClickListener(view -> {
                Intent intent = new Intent(LobbyAccount.this, UpdateAccount.class);
                startActivity(intent);
                finish();
            });

            deleteBtn.setOnClickListener(view -> {
                Intent intent = new Intent(LobbyAccount.this, DeleteAccount.class);
                startActivity(intent);
                finish();
            });

            logoutBtn.setOnClickListener(view -> {
                // Firebase Sign Out
                mAuth.signOut();
                // Google Sign out
                gsc.signOut();
                // Facebook Sign Out
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(LobbyAccount.this, Login.class);
                startActivity(intent);
                finish();
            });

            settingsBtn.setOnClickListener(view -> {
                Intent intent = new Intent(LobbyAccount.this, Settings.class);
                startActivity(intent);
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

