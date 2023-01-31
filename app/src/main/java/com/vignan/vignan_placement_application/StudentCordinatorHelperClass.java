package com.vignan.vignan_placement_application;

public class StudentCordinatorHelperClass {

    private String name;
    private String mail;
    private String mobileNumber;
    private String branch;


    public StudentCordinatorHelperClass(String name, String mail, String mobileNumber, String branch) {
        this.name = name;
        this.mail = mail;
        this.mobileNumber = mobileNumber;
        this.branch = branch;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
    
}
