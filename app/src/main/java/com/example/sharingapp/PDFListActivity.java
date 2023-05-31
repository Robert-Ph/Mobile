package com.example.sharingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFListActivity extends AppCompatActivity {

    private static final String TAG = "PDFListActivity";

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

        String url = "https://drive.google.com/drive/folders/12TvrHL-ly3R3FHSAyHFt5vVJF6LcTlg5?usp=sharing";
        new LoadPDFListTask().execute(url);
    }

    private class LoadPDFListTask extends AsyncTask<String, Void, List<String>> {

        @Override
        protected List<String> doInBackground(String... urls) {
            String url = urls[0];
            List<String> fileList = new ArrayList<>();
            try {
                Document document = Jsoup.connect(url).get();
                Elements fileElements = document.select("div[data-tooltip]");
                System.out.println(fileElements);
                for (Element fileElement : fileElements) {
                    String fileName = fileElement.attr("data-tooltip");
                    if (!fileName.startsWith("Đang tải người dùng")) { // Kiểm tra xem phần tử có chứa tên file và thư mục hay không
                        String[] parts = fileName.split(":");
                        if (parts.length >= 2) {
                            String fileType = parts[0].trim();
                            String fileDetails = parts[1].trim();
                            fileList.add("Loại file: " + fileType + ", " + fileDetails);
                        }
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, "Error loading PDF list: " + e.getMessage());
                e.printStackTrace();
            }

            System.out.println("Số lượng file: " + fileList.size());
            return fileList;
        }


        @Override
        protected void onPostExecute(List<String> fileList) {
            if (fileList != null) {
                adapter.setPDFList(fileList);
                // Print the result to the console
                System.out.println("fileList.size: " + fileList.size());
                for (String fileName : fileList) {
                    System.out.println("File name: " + fileName);
                }
            } else {
                Toast.makeText(PDFListActivity.this, "Error loading PDF list", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class PDFListAdapter extends RecyclerView.Adapter<PDFListAdapter.PDFViewHolder> {

        private List<String> pdfList;

        public void setPDFList(List<String> pdfList) {
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
            String fileName = pdfList.get(position);
            holder.bind(fileName);
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
            }

            public void bind(String fileName) {
                txtFileName.setText(fileName);
            }
        }
    }
}
