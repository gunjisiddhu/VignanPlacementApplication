package com.vignan.vignan_placement_application.student;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

public class StudentCreationPOJO implements Parcelable {

    private String name,regId,password,mail,branch,gender,account_status,otp,authId;

    public StudentCreationPOJO() {
    }

    public StudentCreationPOJO(String name, String regId, String password, String mail, String branch, String gender, String account_status, String otp, String authId) {
        this.name = name;
        this.regId = regId;
        this.password = password;
        this.mail = mail;
        this.branch = branch;
        this.gender = gender;
        this.account_status = account_status;
        this.otp = otp;
        this.authId = authId;
    }

    protected StudentCreationPOJO(Parcel in) {
        name = in.readString();
        regId = in.readString();
        password = in.readString();
        mail = in.readString();
        branch = in.readString();
        gender = in.readString();
        account_status = in.readString();
        otp = in.readString();
        authId = in.readString();
    }

    public static final Creator<StudentCreationPOJO> CREATOR = new Creator<StudentCreationPOJO>() {
        @Override
        public StudentCreationPOJO createFromParcel(Parcel in) {
            return new StudentCreationPOJO(in);
        }

        @Override
        public StudentCreationPOJO[] newArray(int size) {
            return new StudentCreationPOJO[size];
        }
    };

    @Override
    public String toString() {
        return "StudentCreationPOJO{" +
                "name='" + name + '\'' +
                ", regId='" + regId + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", branch='" + branch + '\'' +
                ", gender='" + gender + '\'' +
                ", account_status='" + account_status + '\'' +
                ", otp='" + otp + '\'' +
                ", authId='" + authId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentCreationPOJO)) return false;
        StudentCreationPOJO that = (StudentCreationPOJO) o;
        return Objects.equals(name, that.name) && Objects.equals(regId, that.regId) && Objects.equals(password, that.password) && Objects.equals(mail, that.mail) && Objects.equals(branch, that.branch) && Objects.equals(gender, that.gender) && Objects.equals(account_status, that.account_status) && Objects.equals(otp, that.otp) && Objects.equals(authId, that.authId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, regId, password, mail, branch, gender, account_status, otp, authId);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public String getAccount_status() {
        return account_status;
    }

    public void setAccount_status(String account_status) {
        this.account_status = account_status;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(regId);
        parcel.writeString(password);
        parcel.writeString(mail);
        parcel.writeString(branch);
        parcel.writeString(gender);
        parcel.writeString(account_status);
        parcel.writeString(otp);
        parcel.writeString(authId);
    }
}
