package com.app.devrah.pojo;

/**
 * Created by Rizwan Butt on 18-Jul-17.
 */
public class SentMessagesPojo {
    public String data;

    SentMessagesPojo(String Data){
        this.data=Data;

    }
    public SentMessagesPojo(){

    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}


