package edu.csumb.vill2101.otterairways.fragments;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.csumb.vill2101.otterairways.R;

/**
 * Created by psycho on 5/12/16.
 */
public class ListFlightFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_flight, container, false);
    }
}
