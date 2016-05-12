package edu.csumb.vill2101.otterairways.activities;



import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.csumb.vill2101.otterairways.R;
import edu.csumb.vill2101.otterairways.fragments.ListFlightFragment;
import edu.csumb.vill2101.otterairways.fragments.SearchFlightFragment;

/**
 * Created by psycho on 5/4/16.
 */
public class ReserveSeatActivity extends AppCompatActivity implements SearchFlightFragment.FlightSearch {

    private String destination;
    private String departure;
    private String no_tickets;

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
        this.destination = destination;
        this.departure = departure;
        this.no_tickets = no_tickets;
        ListFlightFragment listFlightFragment = new ListFlightFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.reserve_container, listFlightFragment)
                .addToBackStack(null)
                .commit();
    }
}