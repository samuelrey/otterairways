package edu.csumb.vill2101.otterairways.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.csumb.vill2101.otterairways.models.Account;
import edu.csumb.vill2101.otterairways.models.Flight;


/**
 * Created by psycho on 5/5/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "otter_airways";
    private static int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // Create account table
        String CREATE_ACCOUNT_TABLE = "CREATE TABLE IF NOT EXISTS " + AccountEntry.TABLE_NAME +
                " (" + AccountEntry.COL_USERNAME + " TEXT PRIMARY KEY," +
                AccountEntry.COL_PASSWORD + " TEXT)";

        // Create flight table
        String CREATE_FLIGHT_TABLE = "CREATE TABLE IF NOT EXISTS " + FlightEntry.TABLE_NAME +
                " (" + FlightEntry.COL_FLIGHT_NO + " TEXT PRIMARY KEY," +
                FlightEntry.COL_DEPARTURE + " TEXT," + FlightEntry.COL_ARRIVAL + " TEXT," +
                FlightEntry.COL_TIME + " TEXT," + FlightEntry.COL_CAPACITY + " INTEGER" +
                FlightEntry.COL_PRICE + " REAL)";

        // Create reservation table
        String CREATE_RESERVATION_TABLE = "CREATE TABLE IF NOT EXISTS " + ReservationEntry.TABLE_NAME +
                " (" + ReservationEntry.COL_FLIGHT_NO + " TEXT," +
                ReservationEntry.COL_USERNAME + " TEXT," +
                ReservationEntry.COL_NO_SEATS + " INTEGER," +
                " FOREIGN KEY(" + ReservationEntry.COL_FLIGHT_NO + ") REFERENCES " +
                FlightEntry.TABLE_NAME + "(" + FlightEntry.COL_FLIGHT_NO + ")," +
                " FOREIGN KEY(" + ReservationEntry.COL_USERNAME + ") REFERENCES " +
                AccountEntry.TABLE_NAME + "(" + AccountEntry.COL_USERNAME + ")" +
                " ON DELETE CASCADE);";

        database.execSQL(CREATE_ACCOUNT_TABLE);
        database.execSQL(CREATE_FLIGHT_TABLE);
        database.execSQL(CREATE_RESERVATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        return;
    }

    public void insertAccount(Account account) throws UsernameAlreadyExists {
        if( selectAccount(account.getUsername()) != null ) {
            throw new UsernameAlreadyExists(account.getUsername());
        }

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(AccountEntry.COL_USERNAME, account.getUsername());
        contentValues.put(AccountEntry.COL_PASSWORD, account.getPassword());

        database.insert(AccountEntry.TABLE_NAME, null, contentValues);
        database.close();
    }

    public void insertFlight(Flight flight) throws FlightAlreadyExists {
        if( selectFlight(flight.getFlightNo()) != null ) {
            throw new FlightAlreadyExists(flight.getFlightNo());
        }

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FlightEntry.COL_FLIGHT_NO, flight.getFlightNo());
        contentValues.put(FlightEntry.COL_ARRIVAL, flight.getArrival());
        contentValues.put(FlightEntry.COL_DEPARTURE, flight.getDeparture());
        contentValues.put(FlightEntry.COL_TIME, flight.getTime());
        contentValues.put(FlightEntry.COL_CAPACITY, flight.getCapacity());
        contentValues.put(FlightEntry.COL_PRICE, flight.getPrice());

        database.insert(FlightEntry.TABLE_NAME, null, contentValues);
        database.close();
    }

    public Account selectAccount(String username) {
        Account account = null;

        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + AccountEntry.TABLE_NAME + " WHERE " +
                AccountEntry.COL_USERNAME + " = '" + username + "'";

        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            account = new Account(cursor.getString(0));
        }

        cursor.close();
        database.close();
        return account;
    }

    public Flight selectFlight(String flight_no) {
        Flight flight = null;

        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + FlightEntry.TABLE_NAME + " WHERE " +
                FlightEntry.COL_FLIGHT_NO + " = '" + flight_no + "'";

        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            flight = new Flight(cursor.getString(0));
        }

        cursor.close();
        database.close();
        return flight;
    }

    // table names and columns
    public static abstract class AccountEntry {
        public static final String TABLE_NAME = "account";
        public static final String COL_USERNAME = "username";
        public static final String COL_PASSWORD = "password";
    }

    public static abstract class FlightEntry {
        public static final String TABLE_NAME = "flight";
        public static final String COL_FLIGHT_NO = "flight_no";
        public static final String COL_DEPARTURE = "departure";
        public static final String COL_ARRIVAL = "arrival";
        public static final String COL_TIME = "time";
        public static final String COL_CAPACITY = "capacity";
        public static final String COL_PRICE = "price";
    }

    public static abstract class ReservationEntry {
        public static final String TABLE_NAME = "reservation";
        public static final String COL_FLIGHT_NO = "flight_no";
        public static final String COL_NO_SEATS = "no_seats";
        public static final String COL_USERNAME = "username";
    }

    // database related exceptions
    public class UsernameAlreadyExists extends Exception {
        public UsernameAlreadyExists(String message) {
            super(message);
        }
    }

    public class FlightAlreadyExists extends Exception {
        public FlightAlreadyExists(String message) {
            super(message);
        }
    }
}
