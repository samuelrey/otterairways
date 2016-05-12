package edu.csumb.vill2101.otterairways.activities;



import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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
    private static FragmentManager fragmentManager;
    private static FragmentTransaction fragmentTransaction;
    private static SearchFlightFragment searchFlightFragment;
    private static ListFlightFragment listFlightFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        fragmentManager = getFragmentManager();
        searchFlightFragment = new SearchFlightFragment();
        fragmentManager.beginTransaction()
                            .replace(android.R.id.content, searchFlightFragment)
                            .commit();
    }

    @Override
    public void passFlightData(String destination, String departure, String no_tickets) {
        this.destination = destination;
        this.departure = departure;
        this.no_tickets = no_tickets;
        listFlightFragment = new ListFlightFragment();
        fragmentManager.beginTransaction().replace(android.R.id.content, listFlightFragment).commit();
    }
}