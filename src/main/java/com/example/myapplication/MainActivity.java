package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText usernameEditText = findViewById(R.id.username);
    EditText passwordEditText = findViewById(R.id.password);
    Button loginButton = findViewById(R.id.btn_login);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            boolean enabled = !username.isEmpty() && !password.isEmpty();
            loginButton.setEnabled(enabled);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

    };


}

