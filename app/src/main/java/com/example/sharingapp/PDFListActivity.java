package com.example.sharingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharingapp.ModelDrive.DriveService;
import com.example.sharingapp.ModelDrive.PDFFile;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFListActivity extends AppCompatActivity {

    private static final String TAG = "PDFListActivity";
    private static final String FOLDER_ID = "12TvrHL-ly3R3FHSAyHFt5vVJF6LcTlg5"; // Replace with your folder ID

    private RecyclerView recyclerView;
    private PDFListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdflist);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PDFListAdapter();
        recyclerView.setAdapter(adapter);

        new LoadPDFListTask().execute();
    }

    private class LoadPDFListTask extends AsyncTask<Void, Void, List<PDFFile>> {

        @Override
        protected List<PDFFile> doInBackground(Void... voids) {
            try {
                DriveService driveService = new DriveService(PDFListActivity.this, "credential.json");
                System.out.println(driveService.getPDFFilesInFolder(FOLDER_ID));
                return driveService.getPDFFilesInFolder(FOLDER_ID);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(List<PDFFile> fileList) {
            if (fileList != null) {
                adapter.setPDFList(fileList);
            } else {
                Toast.makeText(PDFListActivity.this, "Error loading PDF list", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class PDFListAdapter extends RecyclerView.Adapter<PDFListAdapter.PDFViewHolder> {

        private List<PDFFile> pdfList;

        public void setPDFList(List<PDFFile> pdfList) {
            this.pdfList = pdfList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public PDFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pdf, parent, false);
            return new PDFViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PDFViewHolder holder, int position) {
            PDFFile pdfFile = pdfList.get(position);
            holder.bind(pdfFile);
        }

        @Override
        public int getItemCount() {
            return pdfList != null ? pdfList.size() : 0;
        }

        public class PDFViewHolder extends RecyclerView.ViewHolder {

            private TextView txtFileName;

            public PDFViewHolder(@NonNull View itemView) {
                super(itemView);
                txtFileName = itemView.findViewById(R.id.txtFileName);

                // Xử lý sự kiện khi người dùng nhấp vào một dòng chữ trong danh sách
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            PDFFile pdfFile = pdfList.get(position);
                            openPDFViewActivity(pdfFile);
                        }
                    }
                });
            }

            public void bind(PDFFile pdfFile) {
                txtFileName.setText(pdfFile.getFileName());
            }
        }
    }

    private void openPDFViewActivity(PDFFile pdfFile) {
        Intent intent = new Intent(this, PDFViewActivity.class);
        intent.putExtra("pdfFile", pdfFile.getFileUrl());
        System.out.println("pdfFileName:" + pdfFile.getFileName() + ",pdfFileURL:" + pdfFile.getFileUrl());
        startActivity(intent);
    }
}


