package edu.csumb.vill2101.otterairways.models;

/**
 * Created by psycho on 5/10/16.
 */
public class Flight {

    private String flight_no;
    private String departure;
    private String arrival;
    private String time;
    private int capacity;
    private double price;

    public Flight(String flight_no) {
        this.flight_no = flight_no;
    }

    public Flight(String flight_no, String departure, String arrival, String time, int capacity, double price) {
        this.flight_no = flight_no;
        this.departure = departure;
        this.arrival = arrival;
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

    public String getArrival() {
        return arrival;
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
}
