package edu.csumb.vill2101.otterairways;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by psycho on 4/28/16.
 */
public class LoginDialog extends DialogFragment {
    public interface PassLoginData {
        void loginData(String username, String password);
    }

    PassLoginData passLoginData;
    LayoutInflater inflater;
    View view;
    EditText username;
    EditText password;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            passLoginData = (PassLoginData) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(e.toString() + " must implement PassLoginData");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedStateInstance) {
        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.login_dialog, null);
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
                        passLoginData.loginData(username.getText().toString(),
                                password.getText().toString());
                        dismiss();
                    }
                });
        builder.setView(view);
        return builder.create();
    }
}