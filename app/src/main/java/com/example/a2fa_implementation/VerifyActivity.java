package com.example.a2fa_implementation;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class VerifyActivity extends AppCompatActivity {
    EditText verificationCodeEditText;
    Button verifyButton;
    Button resendButton;

    String email;
    String expectedCode;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify);

        verificationCodeEditText = findViewById(R.id.verificationCodeEditText);
        verifyButton = findViewById(R.id.verifyButton);
        resendButton = findViewById(R.id.resendButton);

        email = getIntent().getStringExtra("email");
        expectedCode = getIntent().getStringExtra("verificationCode");

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredCode = verificationCodeEditText.getText().toString();

                if (enteredCode.equals(expectedCode)) {
                    Toast.makeText(VerifyActivity.this, "Verification Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VerifyActivity.this, "Incorrect Code, Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expectedCode = MailSender.generateVerificationCode();
                MailSender.sendVerificationEmail(email, expectedCode);
                Toast.makeText(VerifyActivity.this, "A new verification code has been sent to your email.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
