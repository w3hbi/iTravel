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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email_login, password_login;
    private TextView forget_password;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_login    = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);

        forget_password = findViewById(R.id.forget_password);
        forget_password.setOnClickListener(this);

        Button login = findViewById(R.id.login_button);
        login.setOnClickListener(this);

        ImageView btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();


    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_back:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;

            case R.id.login_button:
                userLogin();
                break;

            case R.id.forget_password:
                startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
                break;
        }
    }

    private void userLogin() {

        String email    = email_login.getText().toString().trim();
        String password = password_login.getText().toString().trim();



        if (email.isEmpty()) {
            email_login.setError("email is required!");
            email_login.requestFocus();
            return;            }

        if (password.isEmpty()) {
            password_login.setError("password is required!");
            password_login.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_login.setError("Please provide valid email!");
            email_login.requestFocus();
            return;
        }

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Wait a little bit please");
        dialog.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // redirect to Home layout
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Failed to Login !", Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialog.cancel();

    }
}