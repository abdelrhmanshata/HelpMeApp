package com.shata.helpmyapp.Model;

import java.io.Serializable;

public class ModelTitleDisease implements Serializable {

    String dID;
    String title;

    public ModelTitleDisease() {
    }

    public ModelTitleDisease(String dID, String title) {
        this.dID = dID;
        this.title = title;
    }

    public String getdID() {
        return dID;
    }

    public void setdID(String dID) {
        this.dID = dID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
