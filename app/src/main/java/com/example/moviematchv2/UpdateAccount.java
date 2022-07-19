package com.example.moviematchv2;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UpdateAccount extends AppCompatActivity {

    private EditText nameET, phoneET, passwordET;
    private ImageView profile;
    private ImageButton updateAccnt;

    private FirebaseAuth mAuth;
    private DatabaseReference userDb;

    private String userId, name, phone, password, profileUri;

    private Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);


        nameET = findViewById(R.id.updateName);
        phoneET = findViewById(R.id.updatePhone);
        passwordET = findViewById(R.id.updatePassword);
        updateAccnt = findViewById(R.id.updateButton);
        profile = findViewById(R.id.profileImage);
        updateAccnt = findViewById(R.id.updateButton);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        userDb = FirebaseDatabase.getInstance().getReference().child("users").child("Uid");

        updateAccnt.setOnClickListener(view -> {
            saveUserInformation();
        });

    }

    private void saveUserInformation() {
        name = nameET.getText().toString();
        phone = phoneET.getText().toString();
        password = passwordET.getText().toString();

        Map userInfo = new HashMap();
        userInfo.put("name", name);
        userInfo.put("phone", phone);
        userInfo.put("password", password);

    }
}