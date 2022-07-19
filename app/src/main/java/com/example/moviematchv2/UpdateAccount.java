package com.example.moviematchv2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UpdateAccount extends AppCompatActivity {

    private EditText nameET, phoneET, passwordET;
    private ImageView profilePic;
    private ImageButton updateAccount;

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
        updateAccount = findViewById(R.id.sendRequestButton);
        profilePic = findViewById(R.id.profileImage);


        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);


        profilePic.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
        });

        updateAccount.setOnClickListener(view -> {
            saveUserInformation();
            Intent intent = new Intent(getApplicationContext(), LobbyAccount.class);
            startActivity(intent);
        });
    }

    // Update user information from Users who do not use Google or Facebook
    private void saveUserInformation() {
        if(!nameET.equals("")) {
            name = nameET.getText().toString();
        } else {
            Toast.makeText(UpdateAccount.this, "Must enter new name.", Toast.LENGTH_SHORT).show();
            nameET.requestFocus();
        }
        if(!phoneET.equals("")) {
            phone = phoneET.getText().toString();
        } else {
            Toast.makeText(UpdateAccount.this, "Must enter new phone number.", Toast.LENGTH_SHORT).show();
            nameET.requestFocus();
        }
        if(!passwordET.equals("")) {
            password = passwordET.getText().toString();
        } else {
            Toast.makeText(UpdateAccount.this, "Must enter new password", Toast.LENGTH_SHORT).show();
            passwordET.requestFocus();
        }

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("name", name);
        userInfo.put("phone", phone);
        userInfo.put("password", password);

        userDb.updateChildren(userInfo);

        Toast.makeText(UpdateAccount.this, "Information updated successfully.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK) {
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            profilePic.setImageURI(resultUri);

        }
    }
}