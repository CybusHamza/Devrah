package com.app.devrah.pojo;

/**
 * Created by Hamza Android on 11/6/2017.
 */
public class CardAssociatedCheckBox {
    public int getTotalCheckBoxes() {
        return totalCheckBoxes;
    }

    public void setTotalCheckBoxes(int totalCheckBoxes) {
        this.totalCheckBoxes = totalCheckBoxes;
    }

    public int totalCheckBoxes;
    public String getCheckedCheckBox() {
        return checkedCheckBox;
    }

    public void setCheckedCheckBox(String checkedCheckBox) {
        this.checkedCheckBox = checkedCheckBox;
    }

    String checkedCheckBox;
}
