package com.shata.helpmyapp.Model;

import java.io.Serializable;

public class ModelDisease implements Serializable {

    String dID;
    String title;
    String description;
    String treatments;

    public ModelDisease() {
    }

    public ModelDisease(String dID, String title, String description, String treatments) {
        this.dID = dID;
        this.title = title;
        this.description = description;
        this.treatments = treatments;
    }

    public ModelDisease(String title, String description, String treatments) {
        this.title = title;
        this.description = description;
        this.treatments = treatments;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTreatments() {
        return treatments;
    }

    public void setTreatments(String treatments) {
        this.treatments = treatments;
    }
}
