package com.app.devrah.pojo;

/**
 * Created by AQSA SHaaPARR on 5/31/2017.
 */

public class ProjectsPojo {



    public String data;
    public String boardID;
    public String projectStatus;

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String dueDate;

    public String getBoardAssociatedLabelsId() {
        return boardAssociatedLabelsId;
    }

    public void setBoardAssociatedLabelsId(String boardAssociatedLabelsId) {
        this.boardAssociatedLabelsId = boardAssociatedLabelsId;
    }

    public String boardAssociatedLabelsId;

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public String[] labels;

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String attachment;

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String listId;

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }



    public String getBoardID() {
        return boardID;
    }

    public void setBoardID(String boardID) {
        this.boardID = boardID;
    }

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
