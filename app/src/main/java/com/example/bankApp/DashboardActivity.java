package com.example.bankApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bankApp.model.User;
import com.google.android.material.navigation.NavigationView;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.MenuItem;



public class DashboardActivity extends AppCompatActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener{


    private TextView t1;
    private TextView t11;
    private TextView total_balance;
    private TextView historyTo;
    private TextView type;
    private TextView amount;
    private DBHelper dbhelp;
    private LinearLayout recharge;
    private LinearLayout transfer;
    private LinearLayout bills;
    private List<User> userArrayList;
    private String emailFromIntent;
    private User user;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dbhelp = new DBHelper(this);

        t1 = (TextView) findViewById(R.id.t1);
        t11 = (TextView) findViewById(R.id.t11);
        total_balance = (TextView) findViewById(R.id.total_balance);
        historyTo = (TextView) findViewById(R.id.historyTo);
        type = (TextView) findViewById(R.id.type);
        amount = (TextView) findViewById(R.id.amount);
        transfer = (LinearLayout) findViewById(R.id.transfer);
        recharge = (LinearLayout) findViewById(R.id.recharge);
        bills = (LinearLayout) findViewById(R.id.bills);
        emailFromIntent = getIntent().getStringExtra("EMAIL");

        user = dbhelp.getUser(emailFromIntent);

        t11.setText(String.valueOf((CharSequence) user.getName().toString()));
        total_balance.setText(String.valueOf(user.getBalance()));

        transfer.setOnClickListener(this);
        recharge.setOnClickListener(this);
        bills.setOnClickListener(this);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.navView);

        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }
        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {

            return true;
        }
        switch (item.getItemId()) {
            case R.id.nav_account:
                Intent tIntent = new Intent(DashboardActivity.this, DashboardActivity.class);
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
             Intent tIntent = new Intent(DashboardActivity.this, DashboardActivity.class);
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
        switch (v.getId()) {
            case R.id.transfer:
                Intent tIntent = new Intent(DashboardActivity.this, TransferActivity.class);
                tIntent.putExtra("EMAIL", emailFromIntent);

                startActivity(tIntent);

                break;
                case R.id.recharge:
                Intent rIntent = new Intent(DashboardActivity.this, RechargeActivity.class);
                rIntent.putExtra("EMAIL", emailFromIntent);
                startActivity(rIntent);

                break;
            case R.id.bills:
                Intent bIntent = new Intent(DashboardActivity.this, BillActivity.class);
                bIntent.putExtra("EMAIL", emailFromIntent);
                startActivity(bIntent);

                break;

        }

    }


}
