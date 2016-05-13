package edu.csumb.vill2101.otterairways.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by psycho on 5/10/16.
 */
public class Flight implements Parcelable {

    private String flight_no;
    private String departure;
    private String destination;
    private String time;
    private int capacity;
    private double price;

    private Flight(Parcel in) {
        String[] data = new String[6];

        in.readStringArray(data);
        this.flight_no = data[0];
        this.departure = data[1];
        this.destination = data[2];
        this.time = data[3];
        this.capacity = Integer.parseInt(data[4]);
        this.price = Double.parseDouble(data[5]);
    }

    public Flight(String flight_no) {
        this.flight_no = flight_no;
    }

    public Flight(String flight_no, String departure, String destination, String time, int capacity, double price) {
        this.flight_no = flight_no;
        this.departure = departure;
        this.destination = destination;
        this.time = time;
        this.capacity = capacity;
        this.price = price;
    }

    public String getFlightNo() {
        return flight_no;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    public String getTime() {
        return time;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getPrice() {
        return price;
    }

    public String toString() {
        return flight_no + " " + departure + " " + destination +
                " " + time + " " + Integer.toString(capacity) + " " + Double.toString(price);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.flight_no,
                this.departure,
                this.destination,
                this.time,
                Integer.toString(this.capacity),
                Double.toString(this.price)});
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Flight createFromParcel(Parcel in) {
            return new Flight(in);
        }

        public Flight[] newArray(int size) {
            return new Flight[size];
        }
    };
}
