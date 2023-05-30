package com.example.sharingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sharingapp.Ultils;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Button btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        TextView textViewForgot = findViewById(R.id.forgotPass);
        textViewForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                builder.setTitle("Notion!!!");
                builder.setMessage("If you don't remember your password, create a new account");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng cửa sổ thông báo
                        dialog.dismiss();
                    }
                });

                // Tạo đối tượng AlertDialog từ AlertDialog.Builder và hiển thị nó lên màn hình
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        Button btn_signIn = (Button) findViewById(R.id.btn_doneSignIn);
//        new Ultils().intent_toastNotion(btn_signIn,"Login Successful, SignInActivity.this, MainActivity2.class");
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast_layout, (ViewGroup) findViewById(R.id.custom_toast_container));

                TextView text = layout.findViewById(R.id.text_notion);
                text.setText("Login Successful!!!");

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SignInActivity.this, MainActivity2.class);
                        startActivity(intent);
                    }
                }, 2000 + 1000);
            }
        });


    }

}