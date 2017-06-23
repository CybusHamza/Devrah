package com.app.devrah.pojo;

import java.util.Date;

/**
 * Created by AQSA SHaaPARR on 6/23/2017.
 */

public class AttachmentsPojo {

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



}
