package com.example.sharingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class MainActivity2 extends AppCompatActivity {
    DrawerLayout drawer;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        drawerLayout = findViewById(R.id.drawer_layout);
//        navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()){
//                    case R.id.nav_profile:
//                        Toast.makeText(MainActivity2.this, "Profile", Toast.LENGTH_SHORT).show();
//                        return true;
//                    case R.id.nav_setting:
//                        Toast.makeText(MainActivity2.this, "Setting", Toast.LENGTH_SHORT).show();
//                        return true;
//                    case R.id.ggmaps:
//                        setContentView(R.layout.activity_maps);
//                        return true;
//                }
//                return false;
//            }
//        });


        TextView maps = (TextView) findViewById(R.id.gg_map);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        TextView chatGPT = (TextView) findViewById(R.id.chat_gpt);
        chatGPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MainChatGPT.class);
                startActivity(intent);
            }
        });
        TextView pdfList = (TextView) findViewById(R.id.pdfList);
        pdfList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, PDFListActivity.class);
                startActivity(intent);
            }
        });
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
            Toast.makeText(MainActivity2.this, "No app available to open Google Drive", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }


}