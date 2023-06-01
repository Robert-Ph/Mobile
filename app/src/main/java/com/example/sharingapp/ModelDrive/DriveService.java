package com.example.sharingapp.ModelDrive;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveRequestInitializer;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DriveService {
    private static final String API_KEY = "AIzaSyAc_IdV4w9tQhneBRS-ISkfQrhPDxO12LU"; // Replace with your API key
    private static final String APPLICATION_NAME = "demodrive1";
    private static final String MIME_TYPE_PDF = "application/pdf";

    private Drive driveService;

    public DriveService(Context context, String credentialFileName) throws IOException {
        GoogleCredential credential = getGoogleCredential(context, credentialFileName);
        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        driveService = new Drive.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .setGoogleClientRequestInitializer(new DriveRequestInitializer(API_KEY))
                .build();
    }

    private GoogleCredential getGoogleCredential(Context context, String credentialFileName) throws IOException {
        Gson gson = new Gson();
        InputStream inputStream = context.getAssets().open(credentialFileName);
        InputStreamReader reader = new InputStreamReader(inputStream);
        CredentialData credentialData = gson.fromJson(reader, CredentialData.class);

        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        return new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(jsonFactory)
                .setClientSecrets(credentialData.getInstalled().getClient_id(), null)
                .build();
    }

    public List<PDFFile> getPDFFilesInFolder(String folderId) throws IOException {
        FileList result = driveService.files().list()
                .setQ("'" + folderId + "' in parents and mimeType='" + MIME_TYPE_PDF + "'")
                .setFields("files(name, webContentLink)")
                .execute();

        List<File> files = result.getFiles();
        List<PDFFile> pdfFiles = new ArrayList<>();
        if (files != null && !files.isEmpty()) {
            for (com.google.api.services.drive.model.File file : files) {
                String fileName = file.getName();
                String fileUrl = file.getWebContentLink().replace("view?usp=sharing", "uc?export=download");
                PDFFile pdfFile = new PDFFile(fileName, fileUrl);
                pdfFiles.add(pdfFile);
            }
        }
        return pdfFiles;
    }

    public List<Folder> getFolderList(String parentFolderId) throws IOException {
        FileList result = driveService.files().list()
                .setQ("mimeType='application/vnd.google-apps.folder' and '" + parentFolderId + "' in parents")
                .setFields("files(id, name)")
                .execute();

        List<File> folders = result.getFiles();
        List<Folder> folderList = new ArrayList<>();
        if (folders != null && !folders.isEmpty()) {
            for (File folder : folders) {
                String folderId = folder.getId();
                String folderName = folder.getName();
                Folder folderItem = new Folder(folderName, folderId);
                folderList.add(folderItem);
            }
        }
        System.out.println("folderList:" + folderList);
        return folderList;
    }


}

