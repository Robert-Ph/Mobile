package com.example.sharingapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PDFViewActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {

    private static final String TAG = "PDFViewActivity";
    private static final int REQUEST_CODE_PICK_FILE = 42;

    private PDFView pdfView;
    private Button btnPickFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);

        pdfView = findViewById(R.id.pdfView);
        btnPickFile = findViewById(R.id.btnPickFile);
        btnPickFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFile();
            }
        });

        // Nhận tên file PDF từ Intent
        String fileName = getIntent().getStringExtra("fileName");

        if (fileName != null) {
            // Xử lý hiển thị file PDF dựa trên tên file
            displayPDF(fileName);
        } else {
            Toast.makeText(this, "No PDF file specified", Toast.LENGTH_SHORT).show();
        }
    }

    private void pickFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        try {
            startActivityForResult(intent, REQUEST_CODE_PICK_FILE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No file picker available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_FILE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            displayPDFFromUri(uri);
        }
    }

    private void displayPDF(String fileName) {
        // Đường dẫn đầy đủ đến file PDF
        String filePath = getExternalFilesDir(null) + "/" + fileName;

        File file = new File(filePath);
        if (file.exists()) {
            pdfView.fromFile(file)
                    .defaultPage(0)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .onPageChange(this)
                    .onLoad(this)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();
        } else {
            Toast.makeText(this, "PDF file does not exist", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayPDFFromUri(Uri uri) {
        pdfView.fromUri(uri)
                .defaultPage(0)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .onPageChange(this)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        // Xử lý sự thay đổi trang
    }

    @Override
    public void loadComplete(int nbPages) {
        Toast.makeText(this, "Load thành công!", Toast.LENGTH_SHORT).show();
    }
}

