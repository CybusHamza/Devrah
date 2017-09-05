package com.app.devrah.pojo;

/**
 * Created by Hamza Android on 8/30/2017.
 */

public class check_model {
    String name,id,idchecked;

    private String checked;

    public String getChecked() {
        return checked;
    }

    public String getIdchecked() {
        return idchecked;
    }

    public void setIdchecked(String idchecked) {
        this.idchecked = idchecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String isChecked()
    {
        return checked;
    }

    public void setChecked(String checked)
    {
        this.checked = checked;
    }
}
