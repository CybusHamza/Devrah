package com.app.devrah.pojo;

/**
 * Created by Rizwan Butt on 22-Aug-17.
 */

public class AttachmentsImageFilePojo {
    public String getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(String dateUpload) {
        this.dateUpload = dateUpload;
    }

    public float getSizeOfFile() {
        return sizeOfFile;
    }

    public void setSizeOfFile(long sizeOfFile) {
        this.sizeOfFile = sizeOfFile;
    }

    public String getNameOfFile() {
        return nameOfFile;
    }

    public void setNameOfFile(String nameOfFile) {
        this.nameOfFile = nameOfFile;
    }

    public String dateUpload;
    public long sizeOfFile;
    public  String nameOfFile;

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String imageFile;

}
