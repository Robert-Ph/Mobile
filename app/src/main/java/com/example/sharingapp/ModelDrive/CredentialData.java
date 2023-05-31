package com.example.sharingapp.ModelDrive;

public class CredentialData {
    private InstalledData installed;

    public InstalledData getInstalled() {
        return installed;
    }

    public void setInstalled(InstalledData installed) {
        this.installed = installed;
    }

    @Override
    public String toString() {
        return "CredentialData{" +
                "installed=" + installed +
                '}';
    }
}

