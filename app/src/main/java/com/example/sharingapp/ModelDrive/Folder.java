package com.example.sharingapp.ModelDrive;

public class Folder {
    private String folderName;
    private String folderId;

    public Folder(String folderName, String folderId) {
        this.folderName = folderName;
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "folderName='" + folderName + '\'' +
                ", folderId='" + folderId + '\'' +
                '}';
    }
}
