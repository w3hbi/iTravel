package com.example.itravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    EditText phone_edit, username_edit, email_edit;
    ImageView profile_image;

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;

    private String userID;


    DatabaseReference rootDatabaseref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent data = getIntent();

        String username = data.getStringExtra("username");
        String email = data.getStringExtra("email");
        String phone = data.getStringExtra("phone");

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        username_edit = findViewById(R.id.username_edit);
        email_edit = findViewById(R.id.email_edit);
        phone_edit = findViewById(R.id.phone_edit);

        Button saveBtn = findViewById(R.id.update);

        username_edit.setText(username);
        email_edit.setText(email);
        phone_edit.setText(phone);

        profile_image = findViewById(R.id.profile_image);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        rootDatabaseref = FirebaseDatabase.getInstance().getReference().child("Users");

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username_edit.getText().toString().isEmpty()
                || email_edit.getText().toString().isEmpty()
                || phone_edit.getText().toString().isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "All fields are required !", Toast.LENGTH_SHORT).show();
                } else {

                    user = FirebaseAuth.getInstance().getCurrentUser();
                    userID = user.getUid();

                    String email_e = email_edit.getText().toString();
                    String username_e = username_edit.getText().toString();
                    String phone_e = phone_edit.getText().toString();

                    HashMap hashMap = new HashMap();
                    
                    hashMap.put("email", email_e);
                    hashMap.put("username", username_e);
                    hashMap.put("phone", phone_e);

                    rootDatabaseref.child(userID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(EditProfileActivity.this, "Update succseeful !", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }
                    });

                }
            }
        });
    }

}