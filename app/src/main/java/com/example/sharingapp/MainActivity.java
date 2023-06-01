package com.example.sharingapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
        }
        // event button change view
//        Button btnChangeView = (Button)  findViewById(R.id.changeView);
//        btnChangeView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
//                startActivity(intent);
//            }
//        });
        // event button signIn
        Button btnSignIn = (Button) findViewById(R.id.btn_signIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        Button btnSignUp = (Button) findViewById(R.id.btn_signUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        Button btnTestPDF = (Button) findViewById(R.id.btn_TestPDF);
//        an button
        btnTestPDF.setVisibility(View.GONE);
        btnTestPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PDFListActivity.class);
                startActivity(intent);
            }
        });

        Button btn_UpPDF = findViewById(R.id.btn_UpPDF);
        //        an button
        btn_UpPDF.setVisibility(View.GONE);
        btn_UpPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGoogleDriveFolder("1zAz5aBqNDgDj2aI5TTXBgWTTB3CI5ehl");
            }
        });
    }

    private void openGoogleDriveFolder(String folderId) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://drive.google.com/drive/folders/" + folderId));
        PackageManager packageManager = getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "No app available to open Google Drive", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        // Không thực hiện hành động mặc định của nút Back
        // Ví dụ: không cho phép quay lại hoặc thoát khỏi ứng dụng
    }

}