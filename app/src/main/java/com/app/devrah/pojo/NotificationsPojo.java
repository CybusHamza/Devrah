package com.app.devrah.pojo;

/**
 * Created by Rizwan Butt on 14-Jun-17.
 */

public class NotificationsPojo {
    public String data;

    NotificationsPojo(String Data){
        this.data=Data;

    }
    public NotificationsPojo(){

    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
