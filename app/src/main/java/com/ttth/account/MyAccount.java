package com.ttth.account;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Thanh Hang on 21/01/17.
 */

public class MyAccount implements Parcelable {
    private String id;
    private String name;
    private String phone;
    private String email;

    public MyAccount(String id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.email);
    }

    protected MyAccount(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.email = in.readString();
    }

    public static final Creator<MyAccount> CREATOR = new Creator<MyAccount>() {
        @Override
        public MyAccount createFromParcel(Parcel source) {
            return new MyAccount(source);
        }

        @Override
        public MyAccount[] newArray(int size) {
            return new MyAccount[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return
                "id='" + id +
                ", name='" + name +
                ", phone='" + phone +
                ", email='" + email +"\n";

    }
}
