package edu.csumb.vill2101.otterairways.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.csumb.vill2101.otterairways.models.Account;
import edu.csumb.vill2101.otterairways.models.Flight;
import edu.csumb.vill2101.otterairways.models.Reservation;


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
                FlightEntry.COL_TIME + " TEXT," + FlightEntry.COL_CAPACITY + " INTEGER," +
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

        // Create transaction table
        String CREATE_TRANSACTION_TABLE = "CREATE TABLE IF NOT EXISTS " + TransactionEntry.TABLE_NAME +
                " (" + TransactionEntry.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TransactionEntry.COL_TYPE + " TEXT," +
                TransactionEntry.COL_DETAIL + " TEXT," +
                TransactionEntry.COL_TIME + " TIMESTAMP);";

        database.execSQL(CREATE_ACCOUNT_TABLE);
        database.execSQL(CREATE_FLIGHT_TABLE);
        database.execSQL(CREATE_RESERVATION_TABLE);
        database.execSQL(CREATE_TRANSACTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        try {
            String drop = "DROP TABLE IF EXISTS ";
            database.execSQL(drop + AccountEntry.TABLE_NAME);
            database.execSQL(drop + FlightEntry.TABLE_NAME);
            database.execSQL(drop + ReservationEntry.TABLE_NAME);
            database.execSQL(drop + TransactionEntry.TABLE_NAME);
            onCreate(database);
        } catch(Exception e) {
            Log.d("Otter", e.toString());
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        try {
            String drop = "DROP TABLE IF EXISTS ";
            database.execSQL(drop + AccountEntry.TABLE_NAME);
            database.execSQL(drop + FlightEntry.TABLE_NAME);
            database.execSQL(drop + ReservationEntry.TABLE_NAME);
            database.execSQL(drop + TransactionEntry.TABLE_NAME);
            onCreate(database);
        } catch(Exception e) {
            Log.d("Otter", e.toString());
        }
    }

    // insert
    public void insertAccount(Account account) throws UsernameAlreadyExists {
        if( selectAccount(account.getUsername()) != null ) {
            throw new UsernameAlreadyExists(account.getUsername());
        }
        String type = "Insert Account";
        String details = account.toString();
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(AccountEntry.COL_USERNAME, account.getUsername());
        contentValues.put(AccountEntry.COL_PASSWORD, account.getPassword());

        database.insert(AccountEntry.TABLE_NAME, null, contentValues);
        database.close();

        insertTransaction(type, details);
    }

    public void insertFlight(Flight flight) throws FlightAlreadyExists {
        if( selectFlight(flight.getFlightNo()) != null ) {
            throw new FlightAlreadyExists(flight.getFlightNo());
        }
        String type = "Insert Flight";
        String details = flight.toString();
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FlightEntry.COL_FLIGHT_NO, flight.getFlightNo());
        contentValues.put(FlightEntry.COL_ARRIVAL, flight.getDestination());
        contentValues.put(FlightEntry.COL_DEPARTURE, flight.getDeparture());
        contentValues.put(FlightEntry.COL_TIME, flight.getTime());
        contentValues.put(FlightEntry.COL_CAPACITY, flight.getCapacity());
        contentValues.put(FlightEntry.COL_PRICE, flight.getPrice());

        database.insert(FlightEntry.TABLE_NAME, null, contentValues);
        database.close();

        insertTransaction(type, details);
    }

    public void insertReservation(Flight flight, Account account, int no_tickets) throws NoSuchFlight, NoSuchAccount {
        if( selectFlight(flight.getFlightNo()) == null ) {
            throw new NoSuchFlight(flight.getFlightNo());
        }
        if( selectAccount(account.getUsername()) == null ) {
            throw new NoSuchAccount(account.getUsername());
        }

        String type = "Insert Reservation";
        String details = flight.toString() + " " + account.toString();
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ReservationEntry.COL_FLIGHT_NO, flight.getFlightNo());
        contentValues.put(ReservationEntry.COL_NO_SEATS, Integer.toString(no_tickets));
        contentValues.put(ReservationEntry.COL_USERNAME, account.getUsername());

        database.insert(ReservationEntry.TABLE_NAME, null, contentValues);
        database.close();

        insertTransaction(type, details);
    }

    private void insertTransaction(String type, String details) {
        SQLiteDatabase database = this.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TransactionEntry.COL_TYPE, type);
        contentValues.put(TransactionEntry.COL_DETAIL, details);
        contentValues.put(TransactionEntry.COL_TIME, dateFormat.format(date));

        database.insert(TransactionEntry.TABLE_NAME, null, contentValues);
        Log.d("Transaction", contentValues.toString());
        database.close();
    }

    // select
    public Account selectAccount(String username) {
        Account account = null;

        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + AccountEntry.TABLE_NAME + " WHERE " +
                AccountEntry.COL_USERNAME + " = '" + username + "'";

        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            account = new Account(cursor.getString(0), cursor.getString(1));
        }

        cursor.close();
        database.close();
        return account;
    }

    public Account selectAdmin() {
        Account account = null;

        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + AccountEntry.TABLE_NAME + " WHERE " +
                AccountEntry.COL_USERNAME + " = !admiM2";

        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            account = new Account(cursor.getString(0), cursor.getString(1));
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

    public ArrayList<Flight> selectAllFlights() {
        ArrayList<Flight> flights = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + FlightEntry.TABLE_NAME;

        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                flights.add(new Flight(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getInt(4), cursor.getDouble(5)));
            } while(cursor.moveToNext());
        }

        return flights;
    }

    public ArrayList<Reservation> selectAllReservations(Account account) {
        ArrayList<Reservation> reservations = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + ReservationEntry.TABLE_NAME + " WHERE " +
                ReservationEntry.COL_USERNAME + " = '" + account.getUsername() + "'";
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                reservations.add(new Reservation(cursor.getString(0), Integer.parseInt(cursor.getString(2)),
                        cursor.getString(1)));
            } while(cursor.moveToNext());
        }

        return reservations;
    }

    // delete
    public void deleteReservation(Reservation reservation) {
        String type = "Delete Reservation";
        String details = reservation.toString();
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(ReservationEntry.TABLE_NAME,
                ReservationEntry.COL_FLIGHT_NO + "='" + reservation.getFlightNo() + "' and " +
                ReservationEntry.COL_USERNAME + "='" + reservation.getUsername() + "' and " +
                ReservationEntry.COL_NO_SEATS + "=" + Integer.toString(reservation.getNoTickets()), null);
        database.close();

        insertTransaction(type, details);
    }

    // schemas
    public static abstract class AccountEntry {
        public static final String TABLE_NAME = "account";
        public static final String COL_USERNAME = "username";
        public static final String COL_PASSWORD = "password";
    }

    public static abstract class FlightEntry {
        public static final String TABLE_NAME = "flight";
        public static final String COL_FLIGHT_NO = "flight_no";
        public static final String COL_DEPARTURE = "departure";
        public static final String COL_ARRIVAL = "destination";
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

    public static abstract class TransactionEntry {
        public static final String TABLE_NAME = "tx";
        public static final String COL_ID = "id";
        public static final String COL_TYPE = "type";
        public static final String COL_DETAIL = "detail";
        public static final String COL_TIME = "timestamp";
    }

    // exceptions
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

    public class NoSuchFlight extends Exception {
        public NoSuchFlight(String message) {
            super(message);
        }
    }

    public class NoSuchAccount extends Exception {
        public NoSuchAccount(String message) {
            super(message);
        }
    }
}
