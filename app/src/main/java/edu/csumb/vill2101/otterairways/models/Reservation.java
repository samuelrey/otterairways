package edu.csumb.vill2101.otterairways.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by psycho on 5/13/16.
 */
public class Reservation implements Parcelable {
    private String username;
    private String flight_no;
    private int no_tickets;

    private Reservation(Parcel in) {
        String[] data = new String[3];

        in.readStringArray(data);
        this.username = data[0];
        this.flight_no = data[1];
        this.no_tickets = Integer.parseInt(data[2]);
    }

    public Reservation(String flight_no, int no_tickets, String username) {
        this.flight_no = flight_no;
        this.no_tickets = no_tickets;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getFlightNo() {
        return flight_no;
    }

    public int getNoTickets() {
        return no_tickets;
    }

    public String toString() {
        return username + " " + flight_no + " " + Integer.toString(no_tickets);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.username,
                this.flight_no, Integer.toString(this.no_tickets)});
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Reservation createFromParcel(Parcel in) {
            return new Reservation(in);
        }

        public Reservation[] newArray(int size) {
            return new Reservation[size];
        }
    };
}
