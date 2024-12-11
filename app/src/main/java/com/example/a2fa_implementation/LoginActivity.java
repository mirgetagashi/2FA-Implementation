package com.example.a2fa_implementation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {


    EditText emailEditText, passwordEditText;
    Button loginButton;

    DB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        database = new DB(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill all the fields!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isvalid = database.validateUser(email, password);
                    if (isvalid) {
                        String verificationCode = MailSender.generateVerificationCode();
                        MailSender.sendVerificationEmail(email, verificationCode);
                        Intent intent = new Intent(LoginActivity.this, VerifyActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("verificationCode", verificationCode);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}