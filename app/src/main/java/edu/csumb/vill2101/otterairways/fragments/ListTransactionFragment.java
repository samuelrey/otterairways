package edu.csumb.vill2101.otterairways.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import edu.csumb.vill2101.otterairways.R;
import edu.csumb.vill2101.otterairways.helpers.DatabaseHelper;
import edu.csumb.vill2101.otterairways.models.Flight;
import edu.csumb.vill2101.otterairways.models.Transaction;

/**
 * Created by psycho on 5/12/16.
 */
public class ListTransactionFragment extends ListFragment {

    public interface OnAddFlightClick {
        public void onAddFlightClick();
    }

    OnAddFlightClick i;
    View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            i = (OnAddFlightClick) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(e.toString() + " must implement OnAddFlightClick");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_list_transaction, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DatabaseHelper database = new DatabaseHelper(getContext());
        ArrayList<Transaction> transactions = database.selectAllTransactions();
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.item, transactions);
        setListAdapter(adapter);

        Button button = (Button) view.findViewById(R.id.footer);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.onAddFlightClick();
            }
        });
    }
}
