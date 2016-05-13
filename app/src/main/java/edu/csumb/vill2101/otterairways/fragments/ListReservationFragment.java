package edu.csumb.vill2101.otterairways.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import edu.csumb.vill2101.otterairways.R;
import edu.csumb.vill2101.otterairways.helpers.DatabaseHelper;
import edu.csumb.vill2101.otterairways.models.Account;
import edu.csumb.vill2101.otterairways.models.Flight;
import edu.csumb.vill2101.otterairways.models.Reservation;

/**
 * Created by psycho on 5/13/16.
 */
public class ListReservationFragment extends ListFragment implements AdapterView.OnItemClickListener {

    public interface ReservationList {
        void passReservationData(Reservation reservation);
    }

    ReservationList reservationList;
    ArrayList<Reservation> reservations;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            reservationList = (ReservationList) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(e.toString() + " must implement ReservationList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_reservation, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Account account = getArguments().getParcelable("account");

        DatabaseHelper database = new DatabaseHelper(getActivity());
        reservations = database.selectAllReservations(account);

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.item, reservations);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        reservationList.passReservationData(reservations.get(position));
    }
}