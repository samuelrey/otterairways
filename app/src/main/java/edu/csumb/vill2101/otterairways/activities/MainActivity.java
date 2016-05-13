package edu.csumb.vill2101.otterairways.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.csumb.vill2101.otterairways.helpers.DatabaseHelper;
import edu.csumb.vill2101.otterairways.R;
import edu.csumb.vill2101.otterairways.models.Account;
import edu.csumb.vill2101.otterairways.models.Flight;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button createAccount;
    Button reserveSeat;
    Button cancelReservation;
    Button manageSystem;
    DatabaseHelper database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new DatabaseHelper(this);
        try {
            database.insertAccount(new Account("A@lice5", "@cSit100"));
            database.insertAccount(new Account("$BriAn7", "123aBc#"));
            database.insertAccount(new Account("!chriS12!", "CHrIS12!!"));
            database.insertAccount(new Account("!admiM2", "!admiM2"));
            database.insertFlight(new Flight("Otter101", "Monterey", "Los Angeles", "10:30(AM)", 10, 150.00));
            database.insertFlight(new Flight("Otter102", "Los Angeles", "Monterey", "1:00(PM)", 10, 150.00));
            database.insertFlight(new Flight("Otter201", "Monterey", "Seattle", "11:00(AM)", 5, 200.00));
            database.insertFlight(new Flight("Otter205", "Monterey", "Seattle", "3:45(PM)", 15, 150.00));
            database.insertFlight(new Flight("Otter202", "Seattle", "Monterey", "2:10(PM)", 15, 200.00));
        } catch (DatabaseHelper.UsernameAlreadyExists e) {
            // initial data exists
        } catch (DatabaseHelper.FlightAlreadyExists e) {
            // initial data exists
        }

        createAccount = (Button) findViewById(R.id.create_account);
        createAccount.setOnClickListener(this);

        reserveSeat = (Button) findViewById(R.id.reserve_seat);
        reserveSeat.setOnClickListener(this);

        cancelReservation = (Button) findViewById(R.id.cancel_reservation);
        cancelReservation.setOnClickListener(this);

        manageSystem = (Button) findViewById(R.id.manage_system);
        manageSystem.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if( view.getId() == R.id.create_account ) {
            Intent intent = new Intent(this, CreateAccountActivity.class);
            startActivity(intent);
        }

        else if( view.getId() == R.id.reserve_seat ) {
            Intent intent = new Intent(this, ReserveSeatActivity.class);
            startActivity(intent);
        }

        else if( view.getId() == R.id.cancel_reservation ) {
            Intent intent = new Intent(this, CancelReservationActivity.class);
            startActivity(intent);
        }

        else if( view.getId() == R.id.manage_system ) {
            Intent intent = new Intent(this, ManageSystemActivity.class);
            startActivity(intent);
        }
    }
}
