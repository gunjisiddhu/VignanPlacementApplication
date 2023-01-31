package com.vignan.vignan_placement_application;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class StudentCordinatorHelperClass implements Parcelable {

    private String name;
    private String mail;
    private String mobileNumber;
    private String branch;
    private String gender;
    private String password;

    public StudentCordinatorHelperClass() {
    }

    public StudentCordinatorHelperClass(String name, String mail, String mobileNumber, String branch, String gender, String password) {
        this.name = name;
        this.mail = mail;
        this.mobileNumber = mobileNumber;
        this.branch = branch;
        this.gender = gender;
        this.password = password;
    }

    protected StudentCordinatorHelperClass(Parcel in) {
        name = in.readString();
        mail = in.readString();
        mobileNumber = in.readString();
        branch = in.readString();
        gender = in.readString();
        password = in.readString();
    }

    public static final Creator<StudentCordinatorHelperClass> CREATOR = new Creator<StudentCordinatorHelperClass>() {
        @Override
        public StudentCordinatorHelperClass createFromParcel(Parcel in) {
            return new StudentCordinatorHelperClass(in);
        }

        @Override
        public StudentCordinatorHelperClass[] newArray(int size) {
            return new StudentCordinatorHelperClass[size];
        }
    };

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

    @Override
    public String toString() {
        return "StudentCordinatorHelperClass{" +
                "name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", branch='" + branch + '\'' +
                ", gender='" + gender + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(mail);
        parcel.writeString(mobileNumber);
        parcel.writeString(branch);
        parcel.writeString(gender);
        parcel.writeString(password);
    }
}
