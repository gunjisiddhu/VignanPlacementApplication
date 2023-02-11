package com.vignan.vignan_placement_application.super_admin;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public class StudentData implements Parcelable {
    private String regdNum,fullName,gender,e_mail,phone,authId,branch,year,password,hostelOrDayScholar,tenthMarks,interMarks,overallGrade,backlogCount,aadharCard,panCard;
    List<String> appliedCompanies;
    List<PlacedStudents> placedCompanies;

    public StudentData() {
    }

    public StudentData(String regdNum, String fullName, String gender, String e_mail, String phone, String authId, String branch, String year, String password, String hostelOrDayScholar, String tenthMarks, String interMarks, String overallGrade, String backlogCount, String aadharCard, String panCard, List<String> appliedCompanies, List<PlacedStudents> placedCompanies) {
        this.regdNum = regdNum;
        this.fullName = fullName;
        this.gender = gender;
        this.e_mail = e_mail;
        this.phone = phone;
        this.authId = authId;
        this.branch = branch;
        this.year = year;
        this.password = password;
        this.hostelOrDayScholar = hostelOrDayScholar;
        this.tenthMarks = tenthMarks;
        this.interMarks = interMarks;
        this.overallGrade = overallGrade;
        this.backlogCount = backlogCount;
        this.aadharCard = aadharCard;
        this.panCard = panCard;
        this.appliedCompanies = appliedCompanies;
        this.placedCompanies = placedCompanies;
    }


    protected StudentData(Parcel in) {
        regdNum = in.readString();
        fullName = in.readString();
        gender = in.readString();
        e_mail = in.readString();
        phone = in.readString();
        authId = in.readString();
        branch = in.readString();
        year = in.readString();
        password = in.readString();
        hostelOrDayScholar = in.readString();
        tenthMarks = in.readString();
        interMarks = in.readString();
        overallGrade = in.readString();
        backlogCount = in.readString();
        aadharCard = in.readString();
        panCard = in.readString();
        appliedCompanies = in.createStringArrayList();
        placedCompanies = in.createTypedArrayList(PlacedStudents.CREATOR);
    }

    public static final Creator<StudentData> CREATOR = new Creator<StudentData>() {
        @Override
        public StudentData createFromParcel(Parcel in) {
            return new StudentData(in);
        }

        @Override
        public StudentData[] newArray(int size) {
            return new StudentData[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentData)) return false;
        StudentData that = (StudentData) o;
        return regdNum.equals(that.regdNum) && fullName.equals(that.fullName) && gender.equals(that.gender) && e_mail.equals(that.e_mail) && phone.equals(that.phone) && authId.equals(that.authId) && branch.equals(that.branch) && year.equals(that.year) && password.equals(that.password) && hostelOrDayScholar.equals(that.hostelOrDayScholar) && tenthMarks.equals(that.tenthMarks) && interMarks.equals(that.interMarks) && overallGrade.equals(that.overallGrade) && backlogCount.equals(that.backlogCount) && aadharCard.equals(that.aadharCard) && panCard.equals(that.panCard) && appliedCompanies.equals(that.appliedCompanies) && placedCompanies.equals(that.placedCompanies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regdNum, fullName, gender, e_mail, phone, authId, branch, year, password, hostelOrDayScholar, tenthMarks, interMarks, overallGrade, backlogCount, aadharCard, panCard, appliedCompanies, placedCompanies);
    }

    @Override
    public String toString() {
        return "StudentData{" +
                "regdNum='" + regdNum + '\'' +
                ", fullName='" + fullName + '\'' +
                ", gender='" + gender + '\'' +
                ", e_mail='" + e_mail + '\'' +
                ", phone='" + phone + '\'' +
                ", authId='" + authId + '\'' +
                ", branch='" + branch + '\'' +
                ", year='" + year + '\'' +
                ", password='" + password + '\'' +
                ", hostelOrDayScholar='" + hostelOrDayScholar + '\'' +
                ", tenthMarks='" + tenthMarks + '\'' +
                ", interMarks='" + interMarks + '\'' +
                ", overallGrade='" + overallGrade + '\'' +
                ", backlogCount='" + backlogCount + '\'' +
                ", aadharCard='" + aadharCard + '\'' +
                ", panCard='" + panCard + '\'' +
                ", appliedCompanies=" + appliedCompanies +
                ", placedCompanies=" + placedCompanies +
                '}';
    }


    public String getRegdNum() {
        return regdNum;
    }

    public void setRegdNum(String regdNum) {
        this.regdNum = regdNum;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHostelOrDayScholar() {
        return hostelOrDayScholar;
    }

    public void setHostelOrDayScholar(String hostelOrDayScholar) {
        this.hostelOrDayScholar = hostelOrDayScholar;
    }

    public String getTenthMarks() {
        return tenthMarks;
    }

    public void setTenthMarks(String tenthMarks) {
        this.tenthMarks = tenthMarks;
    }

    public String getInterMarks() {
        return interMarks;
    }

    public void setInterMarks(String interMarks) {
        this.interMarks = interMarks;
    }

    public String getOverallGrade() {
        return overallGrade;
    }

    public void setOverallGrade(String overallGrade) {
        this.overallGrade = overallGrade;
    }

    public String getBacklogCount() {
        return backlogCount;
    }

    public void setBacklogCount(String backlogCount) {
        this.backlogCount = backlogCount;
    }

    public String getAadharCard() {
        return aadharCard;
    }

    public void setAadharCard(String aadharCard) {
        this.aadharCard = aadharCard;
    }

    public String getPanCard() {
        return panCard;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public List<String> getAppliedCompanies() {
        return appliedCompanies;
    }

    public void setAppliedCompanies(List<String> appliedCompanies) {
        this.appliedCompanies = appliedCompanies;
    }

    public List<PlacedStudents> getPlacedCompanies() {
        return placedCompanies;
    }

    public void setPlacedCompanies(List<PlacedStudents> placedCompanies) {
        this.placedCompanies = placedCompanies;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(regdNum);
        parcel.writeString(fullName);
        parcel.writeString(gender);
        parcel.writeString(e_mail);
        parcel.writeString(phone);
        parcel.writeString(authId);
        parcel.writeString(branch);
        parcel.writeString(year);
        parcel.writeString(password);
        parcel.writeString(hostelOrDayScholar);
        parcel.writeString(tenthMarks);
        parcel.writeString(interMarks);
        parcel.writeString(overallGrade);
        parcel.writeString(backlogCount);
        parcel.writeString(aadharCard);
        parcel.writeString(panCard);
        parcel.writeStringList(appliedCompanies);
        parcel.writeTypedList(placedCompanies);
    }
}
