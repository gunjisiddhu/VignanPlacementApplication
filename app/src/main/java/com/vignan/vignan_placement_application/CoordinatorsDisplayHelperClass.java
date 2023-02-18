package com.vignan.vignan_placement_application;

import android.os.Parcel;
import android.os.Parcelable;


public class CoordinatorsDisplayHelperClass implements Parcelable {

    private String name;
    private String branch;
    int image;

    protected CoordinatorsDisplayHelperClass(Parcel in) {
        name = in.readString();
        branch = in.readString();
        image = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(branch);
        dest.writeInt(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CoordinatorsDisplayHelperClass> CREATOR = new Creator<CoordinatorsDisplayHelperClass>() {
        @Override
        public CoordinatorsDisplayHelperClass createFromParcel(Parcel in) {
            return new CoordinatorsDisplayHelperClass(in);
        }

        @Override
        public CoordinatorsDisplayHelperClass[] newArray(int size) {
            return new CoordinatorsDisplayHelperClass[size];
        }
    };

    @Override
    public String toString() {
        return "CoordinatorsDisplayHelperClass{" +
                "name='" + name + '\'' +
                ", branch='" + branch + '\'' +
                ", image=" + image +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public CoordinatorsDisplayHelperClass() {
    }

    public CoordinatorsDisplayHelperClass(String name, String branch, int image) {
        this.name = name;
        this.branch = branch;
        this.image = image;
    }
}
