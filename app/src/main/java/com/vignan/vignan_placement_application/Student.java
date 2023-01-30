package com.vignan.vignan_placement_application;

public class Student {

    private String email;
    private String name;
    private String regdNo;
    private String mobNumber;
    private String branch;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegdNo() {
        return regdNo;
    }

    public void setRegdNo(String regdNo) {
        this.regdNo = regdNo;
    }

    public String getMobNumber() {
        return mobNumber;
    }

    public void setMobNumber(String mobNumber) {
        this.mobNumber = mobNumber;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Student(String email, String name, String regdNo, String mobNumber, String branch) {
        this.email = email;
        this.name = name;
        this.regdNo = regdNo;
        this.mobNumber = mobNumber;
        this.branch = branch;
    }

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", regdNo='" + regdNo + '\'' +
                ", mobNumber='" + mobNumber + '\'' +
                ", branch='" + branch + '\'' +
                '}';
    }
}
