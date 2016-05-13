package edu.csumb.vill2101.otterairways.fragments;

import android.content.Context;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import edu.csumb.vill2101.otterairways.R;
import edu.csumb.vill2101.otterairways.helpers.DatabaseHelper;
import edu.csumb.vill2101.otterairways.models.Flight;

/**
 * Created by psycho on 5/12/16.
 */
public class ListFlightFragment extends ListFragment implements AdapterView.OnItemClickListener {

    public interface FlightList {
        void passFlightData(Flight flight);
    }

    FlightList flightList;
    ArrayList<Flight> flights;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            flightList = (FlightList) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(e.toString() + " must implement FlightList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_flight, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String destination = getArguments().getString("destination");
        String departure = getArguments().getString("departure");
        String no_tickets = getArguments().getString("no_tickets");

        Flight search = new Flight("", departure, destination, "", Integer.parseInt(no_tickets), 0.0);

        DatabaseHelper database = new DatabaseHelper(getActivity());
        flights = database.selectAllFlights();

        for( int i = 0; i < flights.size(); ) {
            if( !search.getDeparture().equals(flights.get(i).getDeparture()) ) {
                flights.remove(i);
                continue;
            }

            if( !search.getDestination().equals(flights.get(i).getDestination()) ) {
                flights.remove(i);
                continue;
            }

            if( search.getCapacity() > flights.get(i).getCapacity() ) {
                flights.remove(i);
                continue;
            }

            i++;
        }

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.item, flights);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        flightList.passFlightData(flights.get(position));
    }
}
