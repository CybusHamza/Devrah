package com.app.devrah.pojo;

/**
 * Created by Rizwan Butt on 14-Jun-17.
 */

public class AcitivitiesPojo {
    public String data;

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String projectId;
    public String boardId;
    public String listId;
    public String cardId;

    public String getDataArray() {
        return dataArray;
    }

    public void setDataArray(String dataArray) {
        this.dataArray = dataArray;
    }

    public String dataArray;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String userName;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String date;

    AcitivitiesPojo(String Data){
        this.data=Data;

    }
    public AcitivitiesPojo(){

    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
