package edu.csumb.vill2101.otterairways.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.csumb.vill2101.otterairways.helpers.DatabaseHelper;
import edu.csumb.vill2101.otterairways.R;
import edu.csumb.vill2101.otterairways.models.Account;

/**
 * Created by psycho on 5/4/16.
 */
public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {

    int counter;
    Button button;
    EditText username;
    EditText password;
    DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        button = (Button) findViewById(R.id.create_account);
        button.setOnClickListener(this);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        database = new DatabaseHelper(this);

        return;
    }

    @Override
    public void onClick(View view) {

        // create account button
        if( view.getId() == R.id.create_account ) {

            // verify username follows format
            if( !username.getText().toString().matches(getString(R.string.create_account_format)) ) {
                counter++;
                Toast.makeText(this, "Username" + getString(R.string.create_account_requires), Toast.LENGTH_LONG).show();
            }

            // verify password follows format
            else if( !password.getText().toString().matches(getString(R.string.create_account_format)) ){
                counter++;
                Toast.makeText(this, "Password" + getString(R.string.create_account_requires), Toast.LENGTH_LONG).show();
            }

            else {

                // try to create account
                // handle username already exists
                try {
                    database.insertAccount(new Account(username.getText().toString(), password.getText().toString()));
                    Toast.makeText(this, getString(R.string.success) + "created account", Toast.LENGTH_LONG).show();
                    finish();
                } catch (DatabaseHelper.UsernameAlreadyExists e) {
                    counter++;
                    Toast.makeText(this, getString(R.string.create_account_exists), Toast.LENGTH_LONG).show();
                }
            }

            // exit if user fails to create
            if( counter > 1 ) {
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("Fail to Create Account")
                        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .create();

                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
        }
    }
}
