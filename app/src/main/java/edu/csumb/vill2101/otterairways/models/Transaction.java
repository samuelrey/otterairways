package edu.csumb.vill2101.otterairways.models;

/**
 * Created by psycho on 5/13/16.
 */
public class Transaction {
    private int id;
    private String type;
    private String detail;
    private String timestamp;

    public Transaction(int id, String type, String detail, String timestamp) {
        this.id = id;
        this.type = type;
        this.detail = detail;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDetail() {
        return detail;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String toString() {
        return Integer.toString(id) + " : " + type + " : " +
                detail + " : " + timestamp;
    }
}
