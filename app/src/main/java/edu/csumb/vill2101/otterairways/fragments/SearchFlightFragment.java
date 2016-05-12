package edu.csumb.vill2101.otterairways.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import edu.csumb.vill2101.otterairways.R;

/**
 * Created by psycho on 5/11/16.
 */
public class SearchFlightFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_flight, container, false);
        ArrayList<String> locations = new ArrayList<>();
        locations.add("Seattle");
        locations.add("Monterey");
        locations.add("Los Angeles");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, locations);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner destination = (Spinner) view.findViewById(R.id.destination);
        destination.setAdapter(arrayAdapter);
        destination.setSelection(0);
        return view;
    }
}
