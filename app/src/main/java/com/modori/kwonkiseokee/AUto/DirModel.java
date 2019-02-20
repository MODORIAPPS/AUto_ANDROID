package com.modori.kwonkiseokee.AUto;

public class DirModel {
    String fileName;
    boolean isImage;

    public DirModel(String fileName, boolean isImage) {
        this.fileName = fileName;
        this.isImage = isImage;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setImage(boolean image) {
        isImage = image;
    }
}
