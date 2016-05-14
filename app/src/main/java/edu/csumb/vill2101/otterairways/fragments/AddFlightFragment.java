package edu.csumb.vill2101.otterairways.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.csumb.vill2101.otterairways.R;

/**
 * Created by psycho on 5/13/16.
 */
public class AddFlightFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_flight, container, false);
    }
}
