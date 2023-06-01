package com.example.sharingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        final TextView gotoLogin = findViewById(R.id.goto_login);
        final EditText emailEditText = findViewById(R.id.email_forgot);
        final Button btn_reset = findViewById(R.id.btn_resetpass);

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();
                if(email.isEmpty()){
                    Toast.makeText(ForgotPassActivity.this,"Fill your email",Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Gửi email thành công, thông báo cho người dùng.
                                    Toast.makeText(ForgotPassActivity.this, "Email sent. Please check your inbox.", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Gửi email thất bại, thông báo cho người dùng.
                                    Toast.makeText(ForgotPassActivity.this, "Failed to send email. Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPassActivity.this,SignInActivity.class));
            }
        });
    }
}