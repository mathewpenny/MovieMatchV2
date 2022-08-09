package com.example.moviematchv2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UpdateAccount extends AppCompatActivity {

    private EditText nameET, phoneET, passwordET;
    private ImageView profilePic;
    private ImageButton updateAccount;
    private Button uploadImg;
    private FirebaseAuth mAuth;
    private DatabaseReference userDb;
    FirebaseStorage storage;
    StorageReference storageReference;
    private Uri filepath;

    private String userId, name, phone, password, profileUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);


        nameET = findViewById(R.id.updateName);
        phoneET = findViewById(R.id.updatePhone);
        passwordET = findViewById(R.id.updatePassword);
        updateAccount = findViewById(R.id.sendRequestButton);
        uploadImg = findViewById(R.id.uploadImg);                
        profilePic = findViewById(R.id.profileImage);
        

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

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
        
        uploadImg.setOnClickListener(view -> {
            uploadImage();
        });
    
    }

    private void uploadImage() {
        if (filepath != null) {
            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());

            // adding listeners on upload or failure of image
            // Progress Listener for loading percentage on the dialog box
            ref.putFile(filepath).addOnSuccessListener(taskSnapshot -> {// Image uploaded successfull Dismiss dialog
                progressDialog.dismiss();
                Toast.makeText(UpdateAccount.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
            })
                    .addOnFailureListener(e -> {  // Error, Image not uploaded
                        progressDialog.dismiss();
                        Toast.makeText(UpdateAccount.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
                    });
            }
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
       /* userInfo.put("profilePic", filepath);*/
        userDb.updateChildren(userInfo);

        Toast.makeText(UpdateAccount.this, "Information updated successfully.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            final Uri imageUri = data.getData();
            filepath = imageUri;
            profilePic.setImageURI(filepath);
            filepath = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                    profilePic.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(UpdateAccount.this, LobbyAccount.class);
        startActivity(intent);
        finish();
    }
}