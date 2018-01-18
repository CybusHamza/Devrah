package com.app.devrah.pojo;

/**
 * Created by Rizwan Butt on 14-Jun-17.
 */

public class NotificationsPojo {
    public String data;

    public String getProjectCreatedBy() {
        return projectCreatedBy;
    }

    public void setProjectCreatedBy(String projectCreatedBy) {
        this.projectCreatedBy = projectCreatedBy;
    }

    public String projectCreatedBy;

    public String getBoardType() {
        return boardType;
    }

    public void setBoardType(String boardType) {
        this.boardType = boardType;
    }

    public String boardType;

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String projectTitle;

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String boardId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String projectId;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String label;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String userName;
    public String date;

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

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    String cardId;

    public String getCardStartDate() {
        return cardStartDate;
    }

    public void setCardStartDate(String cardStartDate) {
        this.cardStartDate = cardStartDate;
    }

    public String getCardDueTime() {
        return cardDueTime;
    }

    public void setCardDueTime(String cardDueTime) {
        this.cardDueTime = cardDueTime;
    }

    public String getCardStartTime() {
        return cardStartTime;
    }

    public void setCardStartTime(String cardStartTime) {
        this.cardStartTime = cardStartTime;
    }

    String cardStartDate;
    String cardDueTime;
    String cardStartTime;

    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    String cardDescription;

    public String getCardDueDate() {
        return cardDueDate;
    }

    public void setCardDueDate(String cardDueDate) {
        this.cardDueDate = cardDueDate;
    }

    String cardDueDate;

    public String getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(String isComplete) {
        this.isComplete = isComplete;
    }

    public String getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(String isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    public String getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
    }

    String isComplete;
    String isSubscribed;
    String isLocked;

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    String listId;

    public String getBoard_name() {
        return board_name;
    }

    public void setBoard_name(String board_name) {
        this.board_name = board_name;
    }

    String board_name;
}
