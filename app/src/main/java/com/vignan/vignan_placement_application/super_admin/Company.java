package com.vignan.vignan_placement_application.super_admin;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Company implements Parcelable {

    private String companyName;
    private String ctc;
    private String dateOfStart;
    private String endOfHiring;
    private String uniqueId;
    private String status;
    private String description;
    private ArrayList<String> branches;

    private ArrayList<String> appliedStudentsList,writtenStudentsList,TRStudentsList,HRStudentsList;

    private ArrayList<PlacedStudents> finalQualifiedList;

    @Override
    public boolean equals(@Nullable Object obj) {
        Company company = (Company) obj;
        return this.getCompanyName().equals(company.getCompanyName()) && this.getBranches().equals(company.getBranches())
                && this.getCtc().equals(company.getCtc()) && this.getDescription().equals(company.getDescription())
                && this.getStatus().equals(company.getStatus());
    }

    protected Company(Parcel in) {
        companyName = in.readString();
        ctc = in.readString();
        dateOfStart = in.readString();
        endOfHiring = in.readString();
        uniqueId = in.readString();
        status = in.readString();
        description = in.readString();
        branches = in.createStringArrayList();
        appliedStudentsList = in.createStringArrayList();
        writtenStudentsList = in.createStringArrayList();
        TRStudentsList = in.createStringArrayList();
        HRStudentsList = in.createStringArrayList();
        finalQualifiedList = in.createTypedArrayList(PlacedStudents.CREATOR);
    }

    public static final Creator<Company> CREATOR = new Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };



    @Override
    public String toString() {
        return "Company{" +
                "companyName='" + companyName + '\'' +
                ", ctc='" + ctc + '\'' +
                ", dateOfStart='" + dateOfStart + '\'' +
                ", endOfHiring='" + endOfHiring + '\'' +
                ", uniqueId='" + uniqueId + '\'' +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", branches=" + branches +
                ", appliedStudentsList=" + appliedStudentsList +
                ", writtenStudentsList=" + writtenStudentsList +
                ", TRStudentsList=" + TRStudentsList +
                ", HRStudentsList=" + HRStudentsList +
                ", finalQualifiedList=" + finalQualifiedList +
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

    public String getEndOfHiring() {
        return endOfHiring;
    }

    public void setEndOfHiring(String endOfHiring) {
        this.endOfHiring = endOfHiring;
    }

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

    public ArrayList<String> getAppliedStudentsList() {
        return appliedStudentsList;
    }

    public void setAppliedStudentsList(ArrayList<String> appliedStudentsList) {
        this.appliedStudentsList = appliedStudentsList;
    }

    public ArrayList<String> getWrittenStudentsList() {
        return writtenStudentsList;
    }

    public void setWrittenStudentsList(ArrayList<String> writtenStudentsList) {
        this.writtenStudentsList = writtenStudentsList;
    }

    public ArrayList<String> getTRStudentsList() {
        return TRStudentsList;
    }

    public void setTRStudentsList(ArrayList<String> TRStudentsList) {
        this.TRStudentsList = TRStudentsList;
    }

    public ArrayList<String> getHRStudentsList() {
        return HRStudentsList;
    }

    public void setHRStudentsList(ArrayList<String> HRStudentsList) {
        this.HRStudentsList = HRStudentsList;
    }

    public ArrayList<PlacedStudents> getFinalQualifiedList() {
        return finalQualifiedList;
    }

    public void setFinalQualifiedList(ArrayList<PlacedStudents> finalQualifiedList) {
        this.finalQualifiedList = finalQualifiedList;
    }

    public Company(String companyName, String ctc, String dateOfStart, String endOfHiring, String uniqueId, String status, String description, ArrayList<String> branches, ArrayList<String> appliedStudentsList, ArrayList<String> writtenStudentsList, ArrayList<String> TRStudentsList, ArrayList<String> HRStudentsList, ArrayList<PlacedStudents> finalQualifiedList) {
        this.companyName = companyName;
        this.ctc = ctc;
        this.dateOfStart = dateOfStart;
        this.endOfHiring = endOfHiring;
        this.uniqueId = uniqueId;
        this.status = status;
        this.description = description;
        this.branches = branches;
        this.appliedStudentsList = appliedStudentsList;
        this.writtenStudentsList = writtenStudentsList;
        this.TRStudentsList = TRStudentsList;
        this.HRStudentsList = HRStudentsList;
        this.finalQualifiedList = finalQualifiedList;
    }
    public Company(String companyName, String ctc, String dateOfStart, String uniqueId, String status, String description, ArrayList<String> branches) {

        this.companyName = companyName;
        this.ctc = ctc;
        this.dateOfStart = dateOfStart;
        this.uniqueId = uniqueId;
        this.status = status;
        this.description = description;
        this.branches = branches;

        this.endOfHiring = null;
        this.appliedStudentsList = null;
        this.writtenStudentsList = null;
        this.TRStudentsList = null;
        this.HRStudentsList = null;
        this.finalQualifiedList = null;
    }
    public Company() {
        this.companyName = null;
        this.ctc = null;
        this.dateOfStart = null;
        this.endOfHiring = null;
        this.uniqueId = null;
        this.status = null;
        this.description = null;
        this.branches = null;
        this.appliedStudentsList = null;
        this.writtenStudentsList = null;
        this.TRStudentsList = null;
        this.HRStudentsList = null;
        this.finalQualifiedList = null;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(companyName);
        parcel.writeString(ctc);
        parcel.writeString(dateOfStart);
        parcel.writeString(endOfHiring);
        parcel.writeString(uniqueId);
        parcel.writeString(status);
        parcel.writeString(description);
        parcel.writeStringList(branches);
        parcel.writeStringList(appliedStudentsList);
        parcel.writeStringList(writtenStudentsList);
        parcel.writeStringList(TRStudentsList);
        parcel.writeStringList(HRStudentsList);
        parcel.writeTypedList(finalQualifiedList);
    }
}
