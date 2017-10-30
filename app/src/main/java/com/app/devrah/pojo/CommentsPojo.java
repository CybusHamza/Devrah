package com.app.devrah.pojo;

/**
 * Created by Hamza Android on 10/24/2017.
 */
public class CommentsPojo {
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    int level;
    String id;

    public String getParentChecklistId() {
        return parentChecklistId;
    }

    public void setParentChecklistId(String parentChecklistId) {
        this.parentChecklistId = parentChecklistId;
    }

    public String getParentfullName() {
        return parentfullName;
    }

    public void setParentfullName(String parentfullName) {
        this.parentfullName = parentfullName;
    }

    public String getParentComment() {
        return parentComment;
    }

    public void setParentComment(String parentComment) {
        this.parentComment = parentComment;
    }

    String parentChecklistId;
    String parentfullName;
    String parentComment;

    public String getParentCardId() {
        return parentCardId;
    }

    public void setParentCardId(String parentCardId) {
        this.parentCardId = parentCardId;
    }

    String parentCardId;

    public String getShowMore() {
        return showMore;
    }

    public void setShowMore(String showMore) {
        this.showMore = showMore;
    }

    String showMore;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    String createdBy;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    String parentId;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    String fileType;

    public String getIsFile() {
        return isFile;
    }

    public void setIsFile(String isFile) {
        this.isFile = isFile;
    }

    String isFile;
    String cardId;

    public String getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(String checklistId) {
        this.checklistId = checklistId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    String checklistId;
    String comments;
    String fullName;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;
}
