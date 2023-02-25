package com.vignan.vignan_placement_application.adapters;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class StudentHighestStatusInCompany implements Parcelable {
    private String uId, roundQualified;

    public StudentHighestStatusInCompany() {
    }

    public StudentHighestStatusInCompany(String uId, String roundQualified) {
        this.uId = uId;
        this.roundQualified = roundQualified;
    }

    protected StudentHighestStatusInCompany(Parcel in) {
        uId = in.readString();
        roundQualified = in.readString();
    }

    public static final Creator<StudentHighestStatusInCompany> CREATOR = new Creator<StudentHighestStatusInCompany>() {
        @Override
        public StudentHighestStatusInCompany createFromParcel(Parcel in) {
            return new StudentHighestStatusInCompany(in);
        }

        @Override
        public StudentHighestStatusInCompany[] newArray(int size) {
            return new StudentHighestStatusInCompany[size];
        }
    };

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getRoundQualified() {
        return roundQualified;
    }

    public void setRoundQualified(String roundQualified) {
        this.roundQualified = roundQualified;
    }

    @Override
    public String toString() {
        return "StudentHighestStatusInCompany{" +
                "uId='" + uId + '\'' +
                ", roundQualified='" + roundQualified + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(uId);
        parcel.writeString(roundQualified);
    }
}
