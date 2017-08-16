package com.app.devrah.pojo;

/**
 * Created by Rizwan Butt on 13-Jun-17.
 */

public class FavouritesPojo {
    public String data,id,P_name,P_status,brdid;

    public String getBrdid() {
        return brdid;
    }

    public void setBrdid(String brdid) {
        this.brdid = brdid;
    }

    public String getP_status() {
        return P_status;
    }

    public void setP_status(String p_status) {
        P_status = p_status;
    }

    public String getP_name() {
        return P_name;
    }

    public void setP_name(String p_name) {
        P_name = p_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
