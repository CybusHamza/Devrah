package com.app.devrah.pojo;

/**
 * Created by AQSA SHaaPARR on 6/7/2017.
 */

public class CardCommentData {



    String cardCommentid;
    String cardName;
    String comment;

    public CardCommentData(String cardCommentid, String cardName, String comment) {
        this.cardCommentid = cardCommentid;
        this.cardName = cardName;
        this.comment = comment;
    }

    public CardCommentData() {
    }


    public String getCardCommentid() {
        return cardCommentid;
    }

    public void setCardCommentid(String cardCommentid) {
        this.cardCommentid = cardCommentid;
    }



    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }




}
