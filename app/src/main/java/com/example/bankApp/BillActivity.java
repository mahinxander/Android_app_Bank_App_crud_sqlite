package com.example.bankApp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.bankApp.model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

public class BillActivity extends AppCompatActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener{

    private TextView transferto;
    private TextView transfertext;

    private EditText edittextaccount;
    private EditText edittextamount;
    private EditText edittexttype;

    private TextInputLayout accountlayout;
    private TextInputLayout layoutamount;

    private AppCompatButton buttonSend;


    private DBHelper help;
    private String emailFromIntent;
    private int send;
    private int receive;
    private int amount;
    private String type;

    private User user;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        help = new DBHelper(this);

        transferto = (TextView) findViewById(R.id.transferto);
        transfertext = (TextView) findViewById(R.id.transfertext);

        accountlayout = (TextInputLayout) findViewById(R.id.accountlayout);
        layoutamount = (TextInputLayout) findViewById(R.id.layoutamount);

        edittextaccount = (EditText) findViewById(R.id.edittextaccount);
        edittextamount = (EditText) findViewById(R.id.edittextamount);
        edittexttype = (EditText) findViewById(R.id.edittexttype);
        buttonSend = (AppCompatButton) findViewById(R.id.buttonSend);

        emailFromIntent = getIntent().getStringExtra("EMAIL");

        user = help.getUser(emailFromIntent);
        transferto.setText("Account NO. "+String.valueOf(user.getaccountID()));
        transfertext.setText("Pay Bill");

        buttonSend.setOnClickListener(this);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.navView);

        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {



        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.nav_account:
                Intent tIntent = new Intent(BillActivity.this, DashboardActivity.class);
                tIntent.putExtra("EMAIL", emailFromIntent);

                startActivity(tIntent);
                return true;
            case R.id.nav_logout:
                finish();
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
                return true;
            default: return super.onOptionsItemSelected(item);

        }

//        return super.onOptionsItemSelected(item);

    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_account:
                Intent tIntent = new Intent(BillActivity.this, DashboardActivity.class);
                tIntent.putExtra("EMAIL", emailFromIntent);

                startActivity(tIntent);
                return true;
            case R.id.nav_logout:
                finish();
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
                return true;
//         default: return super.onOptionsItemSelected(item);
            default: return true;

        }
    }
    @Override
    public void onClick(View v) {
        send = user.getaccountID();
        receive = Integer.parseInt(edittextaccount.getText().toString().trim());
        type = edittexttype.getText().toString().trim();
        amount = Integer.parseInt(edittextamount.getText().toString().trim());
        switch (v.getId()) {

            case R.id.buttonSend:
                        help.bills(send, type, receive, amount);
                        emptyEditText();
                        Context context = getApplicationContext();
                        CharSequence text = "Transferred!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                break;
        }

    }

    private void emptyEditText() {
        edittextaccount.setText(null);
        edittextamount.setText(null);
    }

}
