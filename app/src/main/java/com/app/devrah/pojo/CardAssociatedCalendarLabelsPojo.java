package com.app.devrah.pojo;

/**
 * Created by Hamza Android on 10/23/2017.
 */
public class CardAssociatedCalendarLabelsPojo {
    String[] label;

    public String[] getMembers() {
        return members;
    }

    public void setMembers(String[] members) {
        this.members = members;
    }

    String[] members;
    public String boardAssociatedLabelsId;

    public String getBoardAssociatedLabelsId() {
        return boardAssociatedLabelsId;
    }

    public void setBoardAssociatedLabelsId(String boardAssociatedLabelsId) {
        this.boardAssociatedLabelsId = boardAssociatedLabelsId;
    }

    public String[] getLabelText() {
        return labelText;
    }

    public void setLabelText(String[] labelText) {
        this.labelText = labelText;
    }

    String[] labelText;
    public String[] getLabels() {
        return label;
    }

    public void setLabels(String label[]) {
        this.label = label;
    }

    public String getLabelTextCards() {
        return labelTextCards;
    }

    public void setLabelTextCards(String labelTextCards) {
        this.labelTextCards = labelTextCards;
    }

    public String labelTextCards;

    public String getLabelColorCards() {
        return labelColorCards;
    }

    public void setLabelColorCards(String labelColorCards) {
        this.labelColorCards = labelColorCards;
    }

    public String labelColorCards;
}
