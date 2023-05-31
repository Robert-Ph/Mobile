package com.example.sharingapp;

import java.io.Serializable;

public class PDFFile implements Serializable {
    private String fileName;
    private String fileUrl;

    public PDFFile(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }
}
