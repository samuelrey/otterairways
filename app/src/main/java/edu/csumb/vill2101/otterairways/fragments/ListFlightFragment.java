package edu.csumb.vill2101.otterairways.fragments;

import android.support.v4.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.csumb.vill2101.otterairways.R;

/**
 * Created by psycho on 5/12/16.
 */
public class ListFlightFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_flight, container, false);

        TextView destination = (TextView) view.findViewById(R.id.destination);
        destination.setText(getArguments().getString("destination"));

        TextView departure = (TextView) view.findViewById(R.id.departure);
        departure.setText(getArguments().getString("departure"));

        TextView no_tickets = (TextView) view.findViewById(R.id.no_tickets);
        no_tickets.setText(getArguments().getString("no_tickets"));

        return view;
    }
}
