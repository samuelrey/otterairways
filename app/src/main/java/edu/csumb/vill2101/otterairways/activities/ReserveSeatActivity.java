package edu.csumb.vill2101.otterairways.activities;




import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import edu.csumb.vill2101.otterairways.R;
import edu.csumb.vill2101.otterairways.fragments.ConfirmFragment;
import edu.csumb.vill2101.otterairways.fragments.ListFlightFragment;
import edu.csumb.vill2101.otterairways.fragments.LoginFragment;
import edu.csumb.vill2101.otterairways.fragments.SearchFlightFragment;
import edu.csumb.vill2101.otterairways.helpers.DatabaseHelper;
import edu.csumb.vill2101.otterairways.models.Account;
import edu.csumb.vill2101.otterairways.models.Flight;

/**
 * Created by psycho on 5/4/16.
 */
public class ReserveSeatActivity extends AppCompatActivity implements SearchFlightFragment.FlightSearch, ListFlightFragment.FlightList, LoginFragment.LoginData, ConfirmFragment.ConfirmFlight {

    int no_tickets;
    Flight flight;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        if( findViewById(R.id.reserve_container) != null ) {
            if( savedInstanceState != null ) {
                return;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.reserve_container, new SearchFlightFragment())
                    .commit();
        }
    }

    @Override
    public void passFlightData(String destination, String departure, String no_tickets) {
        this.no_tickets = Integer.parseInt(no_tickets);
        ListFlightFragment listFlightFragment = new ListFlightFragment();
        Bundle bundle = new Bundle();
        bundle.putString("destination", destination);
        bundle.putString("departure", departure);
        bundle.putString("no_tickets", no_tickets);
        listFlightFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.reserve_container, listFlightFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void passFlightData(Flight flight) {
        this.flight = flight;
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.show(getSupportFragmentManager(), "LoginDialog");
    }

    @Override
    public void passLoginData(String username, String password) {
        DatabaseHelper database = new DatabaseHelper(this);
        Account account = database.selectAccount(username);
        if( account != null && account.equals(new Account(username, password)) ) {
            this.account = account;
            ConfirmFragment confirmFragment = new ConfirmFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("flight", this.flight);
            bundle.putParcelable("account", new Account(username, password));
            confirmFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.reserve_container, confirmFragment)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            Toast.makeText(this, R.string.fail, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void confirm() {
        DatabaseHelper database = new DatabaseHelper(this);
        try {
            database.insertReservation(flight, account, no_tickets);
        } catch(DatabaseHelper.NoSuchAccount e) {

        } catch(DatabaseHelper.NoSuchFlight e) {

        }
        finish();
    }
}