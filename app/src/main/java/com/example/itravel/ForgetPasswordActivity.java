package com.example.itravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {


    private EditText emailEditText;
    private Button resetPasswordButton;
    private ProgressBar progressBar;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        emailEditText = findViewById(R.id.email_reset);

        resetPasswordButton = findViewById(R.id.reset);
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {

        String email_pwd = emailEditText.getText().toString();
        
        if (email_pwd.isEmpty()) {
            Toast.makeText(this, "Email field is required", Toast.LENGTH_SHORT).show();
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email_pwd).matches()) {
                Toast.makeText(this, "Please write right email", Toast.LENGTH_SHORT).show();
        } else {

            fAuth.sendPasswordResetEmail(email_pwd).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ForgetPasswordActivity.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ForgetPasswordActivity.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}