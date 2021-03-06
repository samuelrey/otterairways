package edu.csumb.vill2101.otterairways.activities;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import edu.csumb.vill2101.otterairways.R;
import edu.csumb.vill2101.otterairways.fragments.AddFlightFragment;
import edu.csumb.vill2101.otterairways.fragments.ListTransactionFragment;
import edu.csumb.vill2101.otterairways.fragments.LoginFragment;
import edu.csumb.vill2101.otterairways.helpers.DatabaseHelper;
import edu.csumb.vill2101.otterairways.models.Account;
import edu.csumb.vill2101.otterairways.models.Flight;

/**
 * Created by psycho on 5/4/16.
 */
public class ManageSystemActivity extends AppCompatActivity implements LoginFragment.LoginData, ListTransactionFragment.OnAddFlightClick, AddFlightFragment.FlightInformation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        LoginFragment loginFragment = new LoginFragment();
        loginFragment.show(getSupportFragmentManager(), "LoginDialog");
    }

    @Override
    public void passLoginData(String username, String password) {
        DatabaseHelper database = new DatabaseHelper(this);
        if( database.selectAdmin().equals(new Account(username, password)) ) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.manage_container, new ListTransactionFragment())
                    .commit();
        }
        else {
            Toast.makeText(this, R.string.fail, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAddFlightClick() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.manage_container, new AddFlightFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void passFlightInformation(Flight flight) {
        try {
            DatabaseHelper database = new DatabaseHelper(this);
            database.insertFlight(flight);
            Toast.makeText(this, getString(R.string.success) + " created flight.", Toast.LENGTH_LONG).show();
        } catch(DatabaseHelper.FlightAlreadyExists e) {
            Toast.makeText(this, getString(R.string.fail) + " create flight. Flight number already exists", Toast.LENGTH_LONG).show();
        } finally {
            finish();
        }
    }
}