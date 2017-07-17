package com.app.devrah.pojo;

/**
 * Created by AQSA SHaaPARR on 5/31/2017.
 */

public class ProjectsPojo {



    public String data;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String id;

    ProjectsPojo(String Data){
        this.data=Data;

    }
   public ProjectsPojo(){

    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }




}
