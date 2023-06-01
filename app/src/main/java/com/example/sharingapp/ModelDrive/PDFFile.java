package com.example.sharingapp.ModelDrive;

import java.io.Serializable;

public class PDFFile implements Serializable {
    private String fileName;
    private String fileUrl;
    private String folderId;

    public PDFFile(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
    public PDFFile(String fileName, String fileUrl, String folderId) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.folderId = folderId;
    }
    public String getFileName() {
        return fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    @Override
    public String toString() {
        return "PDFFile{" +
                "fileName='" + fileName + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}

