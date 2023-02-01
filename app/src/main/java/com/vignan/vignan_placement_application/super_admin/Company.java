package com.vignan.vignan_placement_application.super_admin;

import java.util.ArrayList;

public class Company {

    private String companyName;
    private String ctc;
    private String dateOfStart;
    private String uniqueId;

    private String status;
    private String description;
    private ArrayList<String> branches;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Company(String companyName, String ctc, String dateOfStart, String uniqueId, String status, String description, ArrayList<String> branches) {
        this.companyName = companyName;
        this.ctc = ctc;
        this.dateOfStart = dateOfStart;
        this.uniqueId = uniqueId;
        this.status = status;
        this.description = description;
        this.branches = branches;
    }

    public Company() {

    }


    @Override
    public String toString() {
        return "Company{" +
                "companyName='" + companyName + '\'' +
                ", ctc='" + ctc + '\'' +
                ", dateOfStart='" + dateOfStart + '\'' +
                ", uniqueId='" + uniqueId + '\'' +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", branches=" + branches +
                '}';
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCtc() {
        return ctc;
    }

    public void setCtc(String ctc) {
        this.ctc = ctc;
    }

    public String getDateOfStart() {
        return dateOfStart;
    }

    public void setDateOfStart(String dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getBranches() {
        return branches;
    }

    public void setBranches(ArrayList<String> branches) {
        this.branches = branches;
    }

}
