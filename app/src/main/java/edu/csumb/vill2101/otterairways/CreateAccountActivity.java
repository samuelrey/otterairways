package edu.csumb.vill2101.otterairways;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by psycho on 5/4/16.
 */
public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    int counter;
    Button button;
    EditText username;
    EditText password;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        button = (Button) findViewById(R.id.create_account);
        button.setOnClickListener(this);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        error = (TextView) findViewById(R.id.error);
    }

    @Override
    public void onClick(View view) {
        if( view.getId() == R.id.create_account ) {
            String format = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*]).*$";
            if( !username.getText().toString().matches(format) ) {
                error.setText(format);
            }
            else if( !password.getText().toString().matches(format) ){
                error.setText("Password must have 1 uppercase letter, 1 lowercase letter, 1 digit and 1 special character");
            }
            else {
                error.setText("Success!");
            }
        }
    }
}
