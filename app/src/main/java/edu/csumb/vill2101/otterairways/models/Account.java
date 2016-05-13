package edu.csumb.vill2101.otterairways.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by psycho on 5/5/16.
 */
public class Account implements Parcelable {

    private String username;
    private String password;

    private Account(Parcel in) {
        String[] data = new String[2];

        in.readStringArray(data);
        this.username = data[0];
        this.password = data[1];
    }

    public Account(String username) {
        this.username = username;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String toString() {
        return username;
    }

    public boolean equals(Object obj) {
        if( obj instanceof Account ) {
            Account account = (Account) obj;
            return this.username.equals(account.username) && this.password.equals(account.password);
        }

        return false;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.username,
                this.password});
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        public Account[] newArray(int size) {
            return new Account[size];
        }
    };
}
