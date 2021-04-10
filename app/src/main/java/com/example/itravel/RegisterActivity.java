package com.example.itravel;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itravel.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username_re, email_re, phone_re, password_re, repassword_re;
    private CountryCodePicker ccp;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username_re   = (EditText) findViewById(R.id.username_add);
        email_re      = (EditText) findViewById(R.id.email_add);
        phone_re      = (EditText) findViewById(R.id.phone_add);
        password_re   = (EditText) findViewById(R.id.password_add);
        repassword_re = (EditText) findViewById(R.id.repassword);

        ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        Button btn_register = (Button) findViewById(R.id.register_button);
        btn_register.setOnClickListener(this);

        ccp = findViewById(R.id.ccp);

        mAuth = FirebaseAuth.getInstance();

    }

    // Get Number with Country Code : Example = +216 93 107 7015
    public String getNumber() {
        return "+"+ ccp.getFullNumber() + phone_re.getText().toString().trim();
    }

    // Buttons action -
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_back :
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.register_button :
                registerUser();
                break;
        }

    }

    private void registerUser() {

        String username   = username_re.getText().toString().trim();
        String email      = email_re.getText().toString().trim();
        String phone      = getNumber();
        String password    = password_re.getText().toString().trim();
        String re_password = repassword_re.getText().toString().trim();
        String image_url = "default";

        if (username.isEmpty()) {
            username_re.setError("Username is required!");
            username_re.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            email_re.setError("email is required!");
            email_re.requestFocus();
            return;        }

        // verify that email is written correct (xyz@xyz.xyz)
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_re.setError("Please provide valid email!");
            email_re.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            phone_re.setError("Phone number is required!");
            phone_re.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            password_re.setError("Username is required!");
            password_re.requestFocus();
            return;
        }

        // verify that password length > 6
        if (password.length() < 6) {
            password_re.setError("minimum length should be 6 characters");
            username_re.requestFocus();
            return;
        }

        if (!password.equals(re_password)) {
            repassword_re.setError("Password does not match !");
            username_re.requestFocus();
            return;
        }



            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("Wait a little bit please");
            dialog.show();


            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                User user = new User(username, email, phone, image_url);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "You have been registered successfully!", Toast.LENGTH_SHORT).show();

                                            // redirect to Main Layout to choose Login or Register action
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();

                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Failed to register! please Try again!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        }
                    });

            dialog.cancel();


    }
}