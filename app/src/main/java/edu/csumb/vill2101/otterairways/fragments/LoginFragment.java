package edu.csumb.vill2101.otterairways.fragments;

import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import edu.csumb.vill2101.otterairways.R;

/**
 * Created by psycho on 4/28/16.
 */
public class LoginFragment extends DialogFragment {
    public interface LoginData {
        void passLoginData(String username, String password);
    }

    LoginData passLoginData;
    LayoutInflater inflater;
    View view;
    EditText username;
    EditText password;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            passLoginData = (LoginData) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(e.toString() + " must implement PassLoginData");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedStateInstance) {
        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_login, null);
        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.login)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        passLoginData.passLoginData(username.getText().toString(),
                                password.getText().toString());
                        dismiss();
                    }
                });
        builder.setView(view);
        return builder.create();
    }
}