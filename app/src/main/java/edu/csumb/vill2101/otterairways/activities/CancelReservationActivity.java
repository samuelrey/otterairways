package edu.csumb.vill2101.otterairways.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import edu.csumb.vill2101.otterairways.R;
import edu.csumb.vill2101.otterairways.fragments.ConfirmCancelFragment;
import edu.csumb.vill2101.otterairways.fragments.ConfirmFragment;
import edu.csumb.vill2101.otterairways.fragments.ListReservationFragment;
import edu.csumb.vill2101.otterairways.fragments.LoginFragment;
import edu.csumb.vill2101.otterairways.helpers.DatabaseHelper;
import edu.csumb.vill2101.otterairways.models.Account;
import edu.csumb.vill2101.otterairways.models.Reservation;

/**
 * Created by psycho on 5/4/16.
 */
public class CancelReservationActivity extends AppCompatActivity implements LoginFragment.LoginData, ListReservationFragment.ReservationList, ConfirmCancelFragment.ConfirmCancel {

    Account account;
    Reservation reservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);

        LoginFragment login = new LoginFragment();
        login.show(getSupportFragmentManager(), "LoginDialog");
    }

    @Override
    public void passLoginData(String username, String password) {
        DatabaseHelper database = new DatabaseHelper(this);
        Account account = database.selectAccount(username);

        if( account != null && account.equals(new Account(username, password)) ) {
            this.account = account;

            ListReservationFragment listReservationFragment = new ListReservationFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("account", new Account(username, password));
            listReservationFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.cancel_container, listReservationFragment)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            Toast.makeText(this, getString(R.string.fail) + "login", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void passReservationData(Reservation reservation) {
        this.reservation = reservation;
        ConfirmCancelFragment confirmCancelFragment = new ConfirmCancelFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("reservation", reservation);
        confirmCancelFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.cancel_container, confirmCancelFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void confirm() {
        DatabaseHelper database = new DatabaseHelper(this);
        database.deleteReservation(reservation);
        Toast.makeText(this, getString(R.string.success) + "canceled reservation", Toast.LENGTH_LONG).show();
        finish();
    }
}