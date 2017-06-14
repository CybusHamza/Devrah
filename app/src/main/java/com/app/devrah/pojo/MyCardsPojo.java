package com.app.devrah.pojo;

/**
 * Created by Rizwan Butt on 14-Jun-17.
 */

public class MyCardsPojo {
    public String data,boardData;

    MyCardsPojo(String Data){
        this.data=Data;

    }
    public MyCardsPojo(){

    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    public String getBoardData(){return boardData;}
    public void setBoardData(String boardData) {
        this.boardData = boardData;
    }
}
