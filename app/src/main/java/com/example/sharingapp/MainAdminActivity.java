package com.example.sharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        TextView mangeFilePDF = (TextView) findViewById(R.id.mangeFilePDF);
        mangeFilePDF.setOnClickListener(new View.OnClickListener() {
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
            Toast.makeText(MainAdminActivity.this, "No app available to open Google Drive", Toast.LENGTH_SHORT).show();
        }
    }
}