package com.example.sharingapp.ModelDrive;

public class InstalledData {
    private String client_id;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    @Override
    public String toString() {
        return "InstalledData{" +
                "client_id='" + client_id + '\'' +
                '}';
    }
}
