package com.example.hamza.lms;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import lms.Employee;
import lms.Network;
import lms.RequestLeave;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        EmployeesRequestsFragment.OnRequestIDSelectedListener {

    private Employee emp;
    private RequestQueue queue;
    private Bundle savedInstanceState;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.savedInstanceState = savedInstanceState;


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLeaveRequest();
            }
        });


        emp = (Employee) getIntent().getSerializableExtra("emp");
        type = getIntent().getStringExtra("type");
        emp.setRole(type);
        queue = Volley.newRequestQueue(this);



        if( findViewById(R.id.fragment_container )!= null) {
            if( savedInstanceState != null){
                return;
            }

            LeaveBalanceFragment leaveBalanceFragment = new
                    LeaveBalanceFragment();
            Bundle args = new Bundle();
            args.putSerializable("emp", emp);
            leaveBalanceFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().add(R.id
                    .fragment_container, leaveBalanceFragment).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView employeeId = (TextView) navigationView.getHeaderView(0)
                .findViewById(R.id
                .employeeID);
        employeeId.setText("Employee ID : " + emp.getEmployeeID());
        TextView role = (TextView) navigationView.getHeaderView(0)
                .findViewById(R.id.role);
        role.setText("Role : " + emp.getRole());
        if(type.compareTo("manager") ==0 || type.compareTo("DRH") ==0){
            MenuItem employeesRequests = (MenuItem) navigationView.getMenu()
                    .findItem(R.id.employeesRequests);
            employeesRequests.setVisible(true);
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_profile) {
            startEditProfile();
            return true;
        } else if(id == R.id.log_out) {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.request_leave) {
                startLeaveRequest();

        } else if (id == R.id.leave_balance) {
            LeaveBalanceFragment leaveBalanceFragment = new
                    LeaveBalanceFragment();
            Bundle args = new Bundle();
            args.putSerializable("emp", emp);
            leaveBalanceFragment.setArguments(args);
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,
                    leaveBalanceFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.leave_history) {
            LeaveHistoryFragment leaveHistoryFragment = new
                    LeaveHistoryFragment();
            Bundle args = new Bundle();
            args.putSerializable("emp", emp);
            leaveHistoryFragment.setArguments(args);

            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,
                    leaveHistoryFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.home) {
            LeaveBalanceFragment leaveBalanceFragment = new
                    LeaveBalanceFragment();
            Bundle args = new Bundle();
            args.putSerializable("emp", emp);
            leaveBalanceFragment.setArguments(args);
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,
                    leaveBalanceFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.employeesRequests) {
            EmployeesRequestsFragment employeesRequestsFragment = new EmployeesRequestsFragment();
            Bundle args = new Bundle();
            args.putSerializable("emp", emp);
            args.putString("type", type);
            employeesRequestsFragment.setArguments(args);
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,
                    employeesRequestsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void startLeaveRequest(){
        RequestLeaveFragment requestLeaveFragment = new
                RequestLeaveFragment();
        Bundle args = new Bundle();
        args.putSerializable("emp", emp);
        requestLeaveFragment.setArguments(args);
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,
                requestLeaveFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void startEditProfile() {
        EditProfileFragment editProfileFragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable("emp", emp);
        editProfileFragment.setArguments(args);
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,
                editProfileFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void logout() {
        Intent intent = new Intent(this, EmployeeLoginActivity.class);
        startActivity(intent);
    }


    @Override
    public void onRequestIDSelectedListener(String requestID,
                                            String employeeID,
                                            String managerID) {
        MyEmployeeFragment myEmployeeFragment = new MyEmployeeFragment();
        Bundle args = new Bundle();
        Employee emp = new Employee(employeeID);
        args.putSerializable("emp",emp);
        args.putString("requestID", requestID);
        args.putString("managerID", managerID);
        args.putString("type", type);
        myEmployeeFragment.setArguments(args);
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,
                myEmployeeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
