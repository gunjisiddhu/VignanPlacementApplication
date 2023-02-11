package com.vignan.vignan_placement_application.super_admin;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class placedStudents implements Parcelable {

    private String regdno,salary;


    protected placedStudents(Parcel in) {
        regdno = in.readString();
        salary = in.readString();
    }

    public static final Creator<placedStudents> CREATOR = new Creator<placedStudents>() {
        @Override
        public placedStudents createFromParcel(Parcel in) {
            return new placedStudents(in);
        }

        @Override
        public placedStudents[] newArray(int size) {
            return new placedStudents[size];
        }
    };

    @Override
    public String toString() {
        return "placedStudents{" +
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

    public placedStudents() {
    }

    public placedStudents(String regdno, String salary) {
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
