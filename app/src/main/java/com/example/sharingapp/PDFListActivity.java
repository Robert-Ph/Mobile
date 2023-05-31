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

    private class LoadPDFListTask extends AsyncTask<String, Void, List<PDFFile>> {

        @Override
        protected List<PDFFile> doInBackground(String... urls) {
            String url = urls[0];
            List<PDFFile> fileList = new ArrayList<>();
            try {
                Document document = Jsoup.connect(url).get();
//                System.out.println("document:" + document);
                Elements fileElements = document.select("div[data-tooltip]");
                for (Element fileElement : fileElements) {
                    String fileName = fileElement.attr("data-tooltip");
                    if (!fileName.startsWith("Đang tải người dùng")) { // Kiểm tra xem phần tử có chứa tên file và thư mục hay không
                        String[] parts = fileName.split(":");
                        if (parts.length >= 2) {
                            String fileType = parts[0].trim();
                            String fileDetails = parts[1].trim();
                            if (fileType.equalsIgnoreCase("PDF")) { // Kiểm tra loại file có phải là PDF không
                                String fileUrl = getFileUrl(fileElement); // Lấy URL của tệp PDF
                                PDFFile pdfFile = new PDFFile(fileName, fileUrl);
                                fileList.add(pdfFile);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, "Error loading PDF list: " + e.getMessage());
                e.printStackTrace();
            }

            return fileList;
        }

        // Phương thức này giúp lấy URL của tệp PDF từ phần tử HTML
        private String getFileUrl(Element fileElement) {
            String imageUrl = fileElement.select("img").attr("src");
            if (imageUrl != null && imageUrl.startsWith("https://lh3.google.com")) {
                String fileId = imageUrl.substring(imageUrl.lastIndexOf("/d/") + 3, imageUrl.indexOf("="));
                return "https://drive.google.com/uc?id=" + fileId;
            }
            return null;
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


