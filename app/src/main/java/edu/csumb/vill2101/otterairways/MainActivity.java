package edu.csumb.vill2101.otterairways;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper database = new DatabaseHelper(this);
        database.insertAccount(new Account("A@lice5", "@cSit100"));
        database.insertAccount(new Account("$BriAn7", "123aBc#"));
        database.insertAccount(new Account("!chriS12!", "CHrIS12!!"));

        Button createAccount = (Button) findViewById(R.id.create_account);
        createAccount.setOnClickListener(this);

        Button reserveSeat = (Button) findViewById(R.id.reserve_seat);
        reserveSeat.setOnClickListener(this);

        Button cancelReservation = (Button) findViewById(R.id.cancel_reservation);
        cancelReservation.setOnClickListener(this);

        Button manageSystem = (Button) findViewById(R.id.manage_system);
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
