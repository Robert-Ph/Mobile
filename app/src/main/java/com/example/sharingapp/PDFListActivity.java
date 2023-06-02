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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharingapp.ModelDrive.DriveService;
import com.example.sharingapp.ModelDrive.Folder;
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
    private static final String FOLDER_ID = "1zAz5aBqNDgDj2aI5TTXBgWTTB3CI5ehl";

    private RecyclerView recyclerView;
    private PDFListAdapter adapter;

    private Spinner spinner;
    private ArrayAdapter<String> spinnerAdapter;
    private List<Folder> folderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdflist);

        spinner = findViewById(R.id.spinner);
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (folderList != null && position >= 0 && position < folderList.size()) {
                    Folder selectedFolder = folderList.get(position);
                    if (selectedFolder.getFolderId().equals(FOLDER_ID)) {
                        new LoadPDFListTask().execute();
                    } else {
                        new LoadPDFListTask().execute(selectedFolder.getFolderId());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PDFListAdapter();
        recyclerView.setAdapter(adapter);

        new LoadFolderListTask().execute(FOLDER_ID);
    }

    private class LoadPDFListTask extends AsyncTask<String, Void, List<PDFFile>> {

        @Override
        protected List<PDFFile> doInBackground(String... folderIds) {
            try {
                List<PDFFile> pdfFiles = new ArrayList<>();
                DriveService driveService = new DriveService(PDFListActivity.this, "credential2.json");
                if (folderIds.length > 0) {
                    for (String folderId : folderIds) {
                        List<PDFFile> filesInFolder = driveService.getPDFFilesInFolder(folderId);
                        pdfFiles.addAll(filesInFolder);
                    }
                } else {
                    List<Folder> subFolders = driveService.getFolderList(FOLDER_ID);
                    for (Folder folder : subFolders) {
                        List<PDFFile> filesInFolder = driveService.getPDFFilesInFolder(folder.getFolderId());
                        pdfFiles.addAll(filesInFolder);
                    }
                }
                return pdfFiles;
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

                // Handle click event when a user taps on a row in the list
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

    private class LoadFolderListTask extends AsyncTask<String, Void, List<Folder>> {

        @Override
        protected List<Folder> doInBackground(String... folderIds) {
            try {
                String folderId = folderIds[0];
                DriveService driveService = new DriveService(PDFListActivity.this, "credential2.json");
                return driveService.getFolderList(folderId);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Folder> folderList) {
            if (folderList != null) {
                PDFListActivity.this.folderList = folderList;

                List<String> folderNames = new ArrayList<>();
                // Add the default folder "Tất cả" to the beginning of the list
                Folder defaultFolder = new Folder("All Files", FOLDER_ID);
                folderList.add(0, defaultFolder);

                for (Folder folder : folderList) {
                    folderNames.add(folder.getFolderName());
                }
                spinnerAdapter.clear();
                spinnerAdapter.addAll(folderNames);
            } else {
                Toast.makeText(PDFListActivity.this, "Error loading folder list", Toast.LENGTH_SHORT).show();
            }
        }
    }
}



