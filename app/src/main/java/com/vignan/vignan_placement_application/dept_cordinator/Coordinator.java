package com.vignan.vignan_placement_application.dept_cordinator;

public class Coordinator {

    private String name;
    private String mail;
    private String mobileNumber;
    private String branch;
    private String gender;
    private String password;
    public Coordinator(String name, String mail, String mobileNumber, String branch, String gender, String password) {

        this.name = name;
        this.mail = mail;
        this.mobileNumber = mobileNumber;
        this.branch = branch;
        this.gender = gender;
        this.password = password;
    }

    public Coordinator() {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
