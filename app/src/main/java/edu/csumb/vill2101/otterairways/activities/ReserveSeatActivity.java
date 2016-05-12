package edu.csumb.vill2101.otterairways.activities;



import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edu.csumb.vill2101.otterairways.R;
import edu.csumb.vill2101.otterairways.fragments.SearchFlightFragment;

/**
 * Created by psycho on 5/4/16.
 */
public class ReserveSeatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SearchFlightFragment searchFlightFragment = new SearchFlightFragment();
        fragmentTransaction.replace(android.R.id.content, searchFlightFragment);
    }
}