package com.app.devrah.pojo;

/**
 * Created by Rizwan Butt on 13-Jun-17.
 */

public class FavouritesPojo {
    public String data;

    FavouritesPojo(String Data){
        this.data=Data;

    }
    public FavouritesPojo(){

    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
