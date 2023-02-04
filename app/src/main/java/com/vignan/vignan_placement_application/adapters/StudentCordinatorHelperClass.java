package com.vignan.vignan_placement_application.adapters;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.vignan.vignan_placement_application.dept_cordinator.Coordinator;

public class StudentCordinatorHelperClass implements Parcelable {
    private String name,authId,mail,mobileNumber,branch,gender,password,username,accountStatus;

    public StudentCordinatorHelperClass() {
    }

    public StudentCordinatorHelperClass(String name, String authId, String mail, String mobileNumber, String branch, String gender, String password, String username, String accountStatus) {
        this.name = name;
        this.authId = authId;
        this.mail = mail;
        this.mobileNumber = mobileNumber;
        this.branch = branch;
        this.gender = gender;
        this.password = password;
        this.username = username;
        this.accountStatus = accountStatus;
    }

    protected StudentCordinatorHelperClass(Parcel in) {
        name = in.readString();
        authId = in.readString();
        mail = in.readString();
        mobileNumber = in.readString();
        branch = in.readString();
        gender = in.readString();
        password = in.readString();
        username = in.readString();
        accountStatus = in.readString();
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

    @Override
    public String toString() {
        return "Coordinator{" +
                "name='" + name + '\'' +
                ", authId='" + authId + '\'' +
                ", mail='" + mail + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", branch='" + branch + '\'' +
                ", gender='" + gender + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", accountStatus='" + accountStatus + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(authId);
        parcel.writeString(mail);
        parcel.writeString(mobileNumber);
        parcel.writeString(branch);
        parcel.writeString(gender);
        parcel.writeString(password);
        parcel.writeString(username);
        parcel.writeString(accountStatus);
    }
}
