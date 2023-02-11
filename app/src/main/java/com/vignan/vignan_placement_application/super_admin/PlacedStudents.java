package com.vignan.vignan_placement_application.super_admin;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class PlacedStudents implements Parcelable {

    private String regdno,salary;


    protected PlacedStudents(Parcel in) {
        regdno = in.readString();
        salary = in.readString();
    }

    public static final Creator<PlacedStudents> CREATOR = new Creator<PlacedStudents>() {
        @Override
        public PlacedStudents createFromParcel(Parcel in) {
            return new PlacedStudents(in);
        }

        @Override
        public PlacedStudents[] newArray(int size) {
            return new PlacedStudents[size];
        }
    };

    @Override
    public String toString() {
        return "PlacedStudents{" +
                "regdno='" + regdno + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }

    public String getRegdno() {
        return regdno;
    }

    public void setRegdno(String regdno) {
        this.regdno = regdno;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public PlacedStudents() {
    }

    public PlacedStudents(String regdno, String salary) {
        this.regdno = regdno;
        this.salary = salary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(regdno);
        parcel.writeString(salary);
    }
}
