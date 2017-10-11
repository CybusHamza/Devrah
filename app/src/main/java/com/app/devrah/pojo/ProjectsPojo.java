package com.app.devrah.pojo;

/**
 * Created by AQSA SHaaPARR on 5/31/2017.
 */

public class ProjectsPojo {



    public String data;
    public String boardID;

    public String getCardAssignedMemberId() {
        return cardAssignedMemberId;
    }

    public void setCardAssignedMemberId(String cardAssignedMemberId) {
        this.cardAssignedMemberId = cardAssignedMemberId;
    }

    public String cardAssignedMemberId;

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String assignedTo;

    public String getReferenceBoardStar() {
        return referenceBoardStar;
    }

    public void setReferenceBoardStar(String referenceBoardStar) {
        this.referenceBoardStar = referenceBoardStar;
    }

    public String referenceBoardStar;

    public String getBoardStar() {
        return boardStar;
    }

    public void setBoardStar(String boardStar) {
        this.boardStar = boardStar;
    }

    public String boardStar;
    public String projectStatus;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String startDate;

    public String getnOfAttachments() {
        return nOfAttachments;
    }

    public void setnOfAttachments(String nOfAttachments) {
        this.nOfAttachments = nOfAttachments;
    }

    public String nOfAttachments;

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

    public String getListColor() {
        return listColor;
    }

    public void setListColor(String listColor) {
        this.listColor = listColor;
    }

    String listColor;

    public String getIsCover() {
        return isCover;
    }

    public void setIsCover(String isCover) {
        this.isCover = isCover;
    }

    String isCover;
    String duetTime;

    public String getDuetTime() {
        return duetTime;
    }

    public void setDuetTime(String duetTime) {
        this.duetTime = duetTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    String startTime;

    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    String cardDescription;

    public String getIsCardComplete() {
        return isCardComplete;
    }

    public void setIsCardComplete(String isCardComplete) {
        this.isCardComplete = isCardComplete;
    }

    String isCardComplete;

    public String getIsCardLocked() {
        return isCardLocked;
    }

    public void setIsCardLocked(String isCardLocked) {
        this.isCardLocked = isCardLocked;
    }

    String isCardLocked;

    public String getIsCardSubscribed() {
        return isCardSubscribed;
    }

    public void setIsCardSubscribed(String isCardSubscribed) {
        this.isCardSubscribed = isCardSubscribed;
    }

    String isCardSubscribed;

    public String getProjectCreatedBy() {
        return projectCreatedBy;
    }

    public void setProjectCreatedBy(String projectCreatedBy) {
        this.projectCreatedBy = projectCreatedBy;
    }

    String projectCreatedBy;

    public String getIsFavouriteFromMembers() {
        return isFavouriteFromMembers;
    }

    public void setIsFavouriteFromMembers(String isFavouriteFromMembers) {
        this.isFavouriteFromMembers = isFavouriteFromMembers;
    }

    String isFavouriteFromMembers;



}
