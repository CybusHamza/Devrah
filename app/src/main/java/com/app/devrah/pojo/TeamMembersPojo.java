package com.app.devrah.pojo;

import android.widget.ImageView;

/**
 * Created by Rizwan Butt on 19-Jun-17.
 */

public class TeamMembersPojo {
    public String data;
    ImageView imageView;

    TeamMembersPojo(String Data){
        this.data=Data;

    }
    public TeamMembersPojo(){

    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    public ImageView getImage(){
        return imageView;
    }
    public void setImage(ImageView imageView){
        this.imageView=imageView;
    }

}
