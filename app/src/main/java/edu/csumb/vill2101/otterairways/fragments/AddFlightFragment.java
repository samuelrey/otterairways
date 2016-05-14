package edu.csumb.vill2101.otterairways.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import edu.csumb.vill2101.otterairways.R;
import edu.csumb.vill2101.otterairways.models.Flight;

/**
 * Created by psycho on 5/13/16.
 */
public class AddFlightFragment extends Fragment {

    public interface FlightInformation {
        public void passFlightInformation(Flight flight);
    }

    FlightInformation flightInformation;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            flightInformation = (FlightInformation) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(e.getMessage() + " must implement FlightInformation");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_flight, container, false);

        final EditText flightText = (EditText) view.findViewById(R.id.flight_no);
        final EditText departText = (EditText) view.findViewById(R.id.departure);
        final EditText destinationText = (EditText) view.findViewById(R.id.destination);
        final EditText timeText = (EditText) view.findViewById(R.id.time);
        final EditText capacityText = (EditText) view.findViewById(R.id.capacity);
        final EditText priceText = (EditText) view.findViewById(R.id.price);
        Button add = (Button) view.findViewById(R.id.add_flight);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Flight flight;
                String flight_no;
                String departure;
                String destination;
                String time;
                int capacity;
                double price;

                flight_no = flightText.getText().toString();
                departure = departText.getText().toString();
                destination = destinationText.getText().toString();
                time = timeText.getText().toString();
                capacity = Integer.parseInt(capacityText.getText().toString());
                price = Double.parseDouble(priceText.getText().toString());
                flight = new Flight(flight_no, departure, destination, time, capacity, price);

                flightInformation.passFlightInformation(flight);
            }
        });

        return view;
    }
}
