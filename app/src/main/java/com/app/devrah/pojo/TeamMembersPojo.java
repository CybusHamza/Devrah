package com.app.devrah.pojo;

/**
 * Created by Rizwan Butt on 19-Jun-17.
 */

public class TeamMembersPojo {
    public String data;
    String imageView;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    String userId;

    public String getGpimageView() {
        return gpimageView;
    }

    public void setGpimageView(String gpimageView) {
        this.gpimageView = gpimageView;
    }

    String gpimageView;

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    String initials;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

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
    public String getImage(){
        return imageView;
    }
    public void setImage(String imageView){
        this.imageView=imageView;
    }

}
