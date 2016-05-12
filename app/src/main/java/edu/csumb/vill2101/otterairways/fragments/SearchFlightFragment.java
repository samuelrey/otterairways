package edu.csumb.vill2101.otterairways.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import edu.csumb.vill2101.otterairways.R;

/**
 * Created by psycho on 5/11/16.
 */
public class SearchFlightFragment extends Fragment {

    public interface FlightSearch {
        void passFlightData(String destination, String departure, String no_tickets);
    }

    View view;
    ArrayAdapter<CharSequence> locationAdapter;
    ArrayAdapter<CharSequence> ticketAdapter;
    FlightSearch flightData;
    Spinner destination;
    Spinner departure;
    Spinner no_tickets;
    Button next;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            flightData = (FlightSearch) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(e.toString() + " must implement FlightSearch");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_flight, container, false);

        locationAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.locations, R.layout.item_spinner);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationAdapter.notifyDataSetChanged();

        ticketAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.tickets, R.layout.item_spinner);
        ticketAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ticketAdapter.notifyDataSetChanged();

        destination = (Spinner) view.findViewById(R.id.destination);
        destination.setAdapter(locationAdapter);
        destination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        departure = (Spinner) view.findViewById(R.id.departure);
        departure.setAdapter(locationAdapter);
        departure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        no_tickets = (Spinner) view.findViewById(R.id.no_tickets);
        no_tickets.setAdapter(ticketAdapter);
        no_tickets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        next = (Button) view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flightData.passFlightData(destination.getSelectedItem().toString(),
                        departure.getSelectedItem().toString(), no_tickets.getSelectedItem().toString());
            }
        });
        return view;
    }
}
