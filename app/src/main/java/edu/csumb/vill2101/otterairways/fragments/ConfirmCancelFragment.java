package edu.csumb.vill2101.otterairways.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.csumb.vill2101.otterairways.R;

/**
 * Created by psycho on 5/12/16.
 */
public class ConfirmCancelFragment extends Fragment {

    ConfirmCancel confirmCancel;

    public interface ConfirmCancel {
        void confirm();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.confirmCancel = (ConfirmCancel) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(e.toString() + " must implement Confirm");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirm_flight, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView details = (TextView) getActivity().findViewById(R.id.details);
        details.setText(getArguments().getParcelable("reservation").toString());

        Button confirm = (Button) getActivity().findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmCancel.confirm();
            }
        });
    }
}
